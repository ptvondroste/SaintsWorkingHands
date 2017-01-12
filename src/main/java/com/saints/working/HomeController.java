package com.saints.working;
+
+import java.text.DateFormat;
+import java.time.LocalDate;
+import java.time.ZoneId;
+import java.util.Calendar;
+import java.util.Date;
+import java.util.Iterator;
+import java.util.List;
+import java.util.Locale;
+
+import javax.servlet.http.Cookie;
+import javax.servlet.http.HttpServletRequest;
+import javax.servlet.http.HttpServletResponse;
+
+import org.slf4j.Logger;
+import org.slf4j.LoggerFactory;
+import org.springframework.stereotype.Controller;
+import org.springframework.ui.Model;
+import org.springframework.web.bind.annotation.RequestMapping;
+import org.springframework.web.bind.annotation.RequestMethod;
+
+
+
+/**
+ * Handles requests for the application home page.
+ */
+@Controller
+public class HomeController {
+
+	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
+
+	// this method is returned from a blank url
+	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
+	public String home(Model model, HttpServletRequest request, HttpServletResponse response) {
+
+		// gets user input from login jsp
+		String username = request.getParameter("username");
+		String password = request.getParameter("password");
+
+		// calls checklogin method and returns a companyprofile object, null if
+		// nonexistant
+		UserProfile user = DAO_DB_Access.checkLogin(username, password);
+		if (user == null) {
+			return "error";
+		}
+
+		// checks for admin username
+		if ((user != null) && user.getSignInName().equalsIgnoreCase("admin")) {
+
+			// makes a cookie with the name admin and value of CompanyID and
+			// adds it to the response
+			Cookie adminID = new Cookie("admin", "" + user.getClientID());
+			response.addCookie(adminID);
+
+			// the rest of this code populates the adminHome page
+
+			// makes list of items with company profile information and donation
+			// information
+			List<itemsForPickup> items = DAO_Donation.getAllItemsForPickup();
+
+			// sets iterator to items for pickup list
+			Iterator<itemsForPickup> iter = items.iterator();
+
+			while (iter.hasNext()) {
+				// sets tempItem to each item in the list
+				itemsForPickup tempItem = iter.next();
+
+				// makes date object for each items expiration date
+				Date itemDate = tempItem.getDonation().getExpirationDate();
+
+				// removes any object thats doesnt have status ready or is
+				// before todays date.
+				if (!(tempItem.getDonation().getStatus().equalsIgnoreCase("ready"))
+						|| (itemDate.before(Calendar.getInstance().getTime()))) {
+					iter.remove();
+				}
+			}
+
+			// add this list to model
+			model.addAttribute("itemList", items);
+
+			return "adminHome";
+		}
+
+		// populates donation form page which is homepage for nonadmin users
+		if (user != null) {
+			model.addAttribute("user", user);
+
+			// adds cookie with corresponding company ID
+			Cookie userCompanyID = new Cookie("userCompanyID", "" + user.getCompanyID());
+			response.addCookie(userCompanyID);
+
+			return "donationform";
+
+		}
+
+		return "error";
+	}
+
+	@RequestMapping(value = "/submittedDonation", method = RequestMethod.POST)
+	public String submittedDonation(Model model, HttpServletRequest request) {
+		// System.out.println(request.getQueryString());
+
+		Donation donation = new Donation();
+
+		// adds days until expiration and makes it a local date object
+		LocalDate expirationLocalDate = LocalDate.now()
+				.plusDays(Integer.parseInt(request.getParameter("expirationDate")));
+
+		// turns localdate into date object for mysql
+		Date date = Date.from(expirationLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
+
+		// sets date of submission to current day
+		Date submissionDate = Calendar.getInstance().getTime();
+
+		// sets parameters to be added into the database
+		donation.setExpirationDate(date);
+		donation.setSubmissionDate(submissionDate);
+		donation.setProductDescription(request.getParameter("productDescription"));
+		donation.setWeight(Integer.parseInt(request.getParameter("enterWeight")));
+		donation.setStatus("ready");
+
+		// looks thru all cookies and finds cookie with name userCompanyID
+		// and sets the donation Company ID to the value of the userCompanyId
+		Cookie[] cookies = request.getCookies();
+
+		for (Cookie c : cookies) {
+			if (c.getName().equalsIgnoreCase("userCompanyID")) {
+				donation.setCompanyID(Integer.parseInt(c.getValue()));
+			}
+		}
+
+		// add donation to database
+		DAO_Donation.addDonation(donation);
+
+		// adds product description to model so we can thank them for it
+		model.addAttribute("productDescription", request.getParameter("productDescription"));
+
+		return "submittedDonation";
+	}
+
+	@RequestMapping(value = "/deletedDonation", method = RequestMethod.GET)
+	public String deletedDonation(Model model, HttpServletRequest request) {
+
+		// Deletes donation from the dao based off company ID
+		Donation deletedDonation = DAO_Donation
+				.deleteDonation(Integer.parseInt(request.getParameter("donationidCompanyDonation")));
+
+		return "deletedDonation";
+	}
+
+	@RequestMapping(value = "/submittedRegistration", method = RequestMethod.POST)
+	public String submittedRegistration(Model model, HttpServletRequest request, HttpServletResponse response) {
+
+		// initializes blank company profile and sets all fields to what was
+		// passed in from registration form
+		CompanyProfile cp = new CompanyProfile();
+
+		cp.setCompanyName(request.getParameter("companyName"));
+		cp.setAddress(request.getParameter("address"));
+		cp.setMainContact(request.getParameter("contact"));
+		cp.setCompanyPhoneNumber(request.getParameter("phoneNumber"));
+		cp.setEmail(request.getParameter("email"));
+		cp.setTwitterName(request.getParameter("twitter"));
+		cp.setUserName(request.getParameter("username"));
+		cp.setPassword(request.getParameter("password"));
+
+		// adds company profile to database and returns id
+		int id = DAO_Profile.addCompanyProfile(cp);
+
+		// if company id == 0, username already exists, and sends them to
+		// registrationTaken page, does not add anything to databse
+		if (id == 0) {
+			model.addAttribute("username", request.getParameter("username"));
+			return "registrationTaken";
+		}
+
+		// if id is not 0, adds company to database, and sends them to
+		// submittedRegistration page
+		model.addAttribute("companyName", request.getParameter("companyName"));
+
+		return "submittedRegistration";
+	}
+
+	@RequestMapping(value = "/adminHome", method = RequestMethod.GET)
+	public String adminHome(Model model, HttpServletRequest request) throws UnsupportedOperationException {
+		/*
+		 * String validateAdmin = "adminID"; Cookie[] cookies =
+		 * request.getCookies(); String adminCookie = ""; for (Cookie c :
+		 * cookies) { if (c.getName().equalsIgnoreCase("adminID")) { adminCookie
+		 * = c.getName(); } }
+		 * 
+		 * if (validateAdmin.equalsIgnoreCase(adminCookie)) {
+		 */
+		
+		// makes list of items with company profile information and donation
+		// information
+		List<itemsForPickup> items = DAO_Donation.getAllItemsForPickup();
+
+		// sets iterator to items for pickup list
+		Iterator<itemsForPickup> iter = items.iterator();
+
+		while (iter.hasNext()) {
+			// sets tempItem to each item in the list
+			itemsForPickup tempItem = iter.next();
+
+			// makes date object for each items expiration date
+			Date itemDate = tempItem.getDonation().getExpirationDate();
+
+			// removes any object thats doesnt have status ready or is
+			// before todays date.
+			if (!(tempItem.getDonation().getStatus().equalsIgnoreCase("ready"))
+					|| (itemDate.before(Calendar.getInstance().getTime()))) {
+				iter.remove();
+			}
+		}
+
+		// add this list to model
+		model.addAttribute("itemList", items);
+
+		return "adminHome";
+		/*
+		 * } else { return "error"; }
+		 */
+	}
+
+	// sends you to login page
+	@RequestMapping(value = "/", method = RequestMethod.GET)
+	public String login(Model model, HttpServletRequest request) {
+
+		return "login";
+	}
+
+	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
+	public String confirm(Model model, HttpServletRequest request) throws TwitterException {
+		
+		//changes status of donation to completed
+		DAO_Donation.confirmDonation(Integer.parseInt(request.getParameter("confirm")));
+		
+		//sets up twitter for sending tweet, calling on the props class for the information
+		ConfigurationBuilder cb = new ConfigurationBuilder();
+
+		cb.setDebugEnabled(true).setOAuthConsumerKey(Props.KEY).setOAuthConsumerSecret(Props.Secret)
+				.setOAuthAccessToken(Props.Token).setOAuthAccessTokenSecret(Props.TokenSecret);
+
+		TwitterFactory tf = new TwitterFactory(cb.build());
+		twitter4j.Twitter tw = tf.getInstance();
+		
+		//gets the fields from the object
+		String tweetName = request.getParameter("tweet");
+		String companyName = request.getParameter("companyName");
+		String productDescription = request.getParameter("productDescription");
+		
+		//if they have no twitter, tweets to the company, if they have twitter, tweets to the companys twitter name
+		if (tweetName.equalsIgnoreCase("")) {
+			Status stat = tw.updateStatus("Thank you @" + companyName + " for the " + productDescription + "!");
+			// System.out.println("tweetname is null");
+		} else {
+			Status stat = tw.updateStatus("Thank you @" + tweetName + " for " + productDescription + "!");
+			// System.out.println("Twitter updated");
+		}
+
+		return "confirm";
+	}
+
+	@RequestMapping(value = "/CompanyDonations", method = RequestMethod.GET)
+	public String CompanyDonations(Model model, HttpServletRequest request) {
+
+		//gets list of items for pickup which contain company profile and donation information
+		List<itemsForPickup> items = DAO_Donation.getAllItemsForPickup();
+
+		int companyID = 0;
+		
+		//gets the cookie with companyID saves it as companyID
+		Cookie[] cookies = request.getCookies();
+		for (Cookie c : cookies) {
+			if (c.getName().equalsIgnoreCase("userCompanyID")) {
+				companyID = (Integer.parseInt(c.getValue()));
+				// System.out.println(companyID);
+			}
+		}
+		
+		//loops thru all items and removes any whose ID does not equal company ID
+		Iterator<itemsForPickup> iter = items.iterator();
+		while (iter.hasNext()) {
+			if (iter.next().getDonation().getCompanyID() != companyID) {
+				iter.remove();
+			}
+		}
+		
+		model.addAttribute("companyName", items.get(0).getCompany().getCompanyName());
+		model.addAttribute("itemList", items);
+
+		return "CompanyDonations";
+	}
+
+	@RequestMapping(value = "/adminRecentDonations", method = RequestMethod.GET)
+	public String adminRecentDonations(Model model, HttpServletRequest request) {
+		// referencing ItemsForPickup object to build table
+		List<itemsForPickup> items = DAO_Donation.getAllItemsForPickup();
+
+		//loops thru all donations and removes any that are not complete or are before 1 month ago.
+		Iterator<itemsForPickup> iter = items.iterator();
+		while (iter.hasNext()) {
+			itemsForPickup tempItem = iter.next();
+			
+			//gets donations expirations date
+			Date itemDate = tempItem.getDonation().getExpirationDate();
+			
+			//sets a date to a month ago
+			Calendar oneMonthAgo = Calendar.getInstance();
+			oneMonthAgo.add(Calendar.MONTH, -1);
+
+			//removes any donations that are not complete or are before 1 month ago.
+			if (!(tempItem.getDonation().getStatus().equalsIgnoreCase("complete"))
+					|| (itemDate.before(oneMonthAgo.getTime()))) {
+				iter.remove();
+			}
+		}
+
+		model.addAttribute("itemList", items);
+
+		return "adminRecentDonations";
+	}
+
+	@RequestMapping(value = "/adminAllDonations", method = RequestMethod.GET)
+	public String adminAllDonations(Model model, HttpServletRequest request) {
+		// referencing ItemsForPickup object to build table
+		List<itemsForPickup> items = DAO_Donation.getAllItemsForPickup();
+
+		//loops thru all donations and removes any that are not complete 
+		Iterator<itemsForPickup> iter = items.iterator();
+		while (iter.hasNext()) {
+			itemsForPickup tempItem = iter.next();
+			
+			//removes any that are not complete 
+			if (!(tempItem.getDonation().getStatus().equalsIgnoreCase("complete"))) {
+				iter.remove();
+			}
+		}
+
+		model.addAttribute("itemList", items);
+
+		return "adminAllDonations";
+	}
+
+	@RequestMapping(value = "/remove", method = RequestMethod.GET)
+	public String cancel(Model model, HttpServletRequest request) {
+		DAO_Donation.deleteDonation(Integer.parseInt(request.getParameter("remove")));
+
+		return "remove";
+	}
+
+	@RequestMapping(value = "/error", method = RequestMethod.GET)
+	public String error(Model model, HttpServletRequest request) {
+
+		return "error";
+	}
+
+	@RequestMapping(value = "/donationform", method = { RequestMethod.GET, RequestMethod.POST })
+	public String donationForm(Model model, HttpServletRequest request) {
+
+		return "donationform";
+	}
+	
+	//remove donation by company ID
+	@RequestMapping(value = "/removeFromCompanyDonationPage", method = RequestMethod.GET)
+	public String removeFromCompanyDonationPage(Model model, HttpServletRequest request) {
+		//deletes donation based off companyID from hidden form on corresponding page
+		DAO_Donation.deleteDonation(Integer.parseInt(request.getParameter("cancel")));
+
+		return "removeFromCompanyDonationPage";
+	}
+	
+	//remove donation by company ID
+	@RequestMapping(value = "/removeFromRecentDonations", method = RequestMethod.GET)
+	public String removeFromRecentDonations(Model model, HttpServletRequest request) {
+		//deletes donation based off companyID from hidden form on corresponding page
+		DAO_Donation.deleteDonation(Integer.parseInt(request.getParameter("remove")));
+
+		return "removeFromRecentDonations";
+	}
+	
+	//remove donation by company ID
+	@RequestMapping(value = "/removeFromAllDonations", method = RequestMethod.GET)
+	public String removeFromAllDonations(Model model, HttpServletRequest request) {
+		//deletes donation based off companyID from hidden form on corresponding page
+		DAO_Donation.deleteDonation(Integer.parseInt(request.getParameter("remove")));
+
+		return "removeFromAllDonations";
+	}
+
+	@RequestMapping(value = "/editedRegistration", method = RequestMethod.GET)
+	public String editedRegistration(Model model, HttpServletRequest request) {
+
+		int companyID = 0;
+		
+		//uses cookie from login to get the companyID
+		Cookie[] cookies = request.getCookies();
+		for (Cookie c : cookies) {
+			if (c.getName().equalsIgnoreCase("userCompanyID")) {
+				companyID = (Integer.parseInt(c.getValue()));
+				// System.out.println(companyID);
+			}
+		}
+		
+		//gets the company by the id to pre populate the page
+		CompanyProfile companyToEdit = DAO_Profile.getCompanyProfileById(companyID);
+		model.addAttribute("companyProfile", companyToEdit);
+		
+		return "editedRegistration";
+	}
+	
+	@RequestMapping(value = "/editedConfirmation", method = RequestMethod.POST)
+	public String editedConfirmation(Model model, HttpServletRequest request) {
+	//initializes a blank company profile object to add changed parameters.
+	CompanyProfile cp = new CompanyProfile();
+
+	cp.setCompanyName(request.getParameter("companyName"));
+	cp.setAddress(request.getParameter("address"));
+	cp.setMainContact(request.getParameter("contact"));
+	cp.setCompanyPhoneNumber(request.getParameter("phoneNumber"));
+	cp.setEmail(request.getParameter("email"));
+	cp.setTwitterName(request.getParameter("twitter"));
+	cp.setUserName(request.getParameter("username"));
+	cp.setCompanyID(Integer.parseInt(request.getParameter("companyID")));
+	cp.setPassword(request.getParameter("password"));
+	
+	DAO_Profile.updateCompanyProfile(cp);
+	
+	return "editedConfirmation";
+	}
+}
+
+
+
+
+/*
+
+ // Handles requests for the application home page.
+
+@Controller
+public class HomeController {
+	
+	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
+	
+	
+	 // Simply selects the home view to render by returning its name.
+	 
+	@RequestMapping(value = "/", method = RequestMethod.GET)
+	public String home(Locale locale, Model model) {
+		logger.info("Welcome home! The client locale is {}.", locale);
+		
+		Date date = new Date();
+		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
+		
+		String formattedDate = dateFormat.format(date);
+		
+		model.addAttribute("serverTime", formattedDate );
+		
+		return "home";
+	}
+	
+}
+*/