package com.attendance.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.attendance.entity.SignDetail;
import com.attendance.entity.User;
import com.attendance.model.Signinoff;
import com.attendance.repository.SignRepo;
import com.attendance.repository.UserRepo;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/")
public class LoginController {
	
	@Autowired
	private SignRepo srepo;
	
	@Autowired
	private UserRepo urepo;
	
	private String  previousSignDate = "";
	
	@Autowired
	private BCryptPasswordEncoder passwordencoder;
	
	@GetMapping
	public String getHome()
	{
		System.out.println(" in /");
		return "index";
	}
	
	@GetMapping("/register")
	public String doRegisterpage() 
	{
		System.out.println(" in /register");
		return "register";
	}

	@PostMapping("/doregister")
	public String doRegister(@ModelAttribute User user) 
	{
		System.out.println(" in /doregister");
		if(!user.getUsername().equals("") && !user.getPassword().equals("")&& !user.getEmailId().equals("") && !user.getPhoneNO().equals(""))
		{
			String role = "user";
			if(user.getUsername().equals("admin"))
				role = "admin";
			User uentity = new User();	
			uentity.setEmailId(user.getEmailId());
			uentity.setUsername(user.getUsername());
			uentity.setPassword(passwordencoder.encode(user.getPassword()));
			uentity.setPhoneNO(user.getPhoneNO());
			uentity.setRole(role);
			uentity.setEnabled(true);
			uentity.setType(1);
			urepo.save(uentity);
		}
		return "index";
		
	}
	
	@PostMapping("/checklogin")
	public String checkLogin(@ModelAttribute User user )//maintain Httpsession
	{ 
		System.out.println("in /checklogin");
		if(!user.getUsername().equals("")&& !user.getPassword().equals("")) {
			
			if(user.getUsername().equals("admin"))
			{
				//session.setAttribute("user", user.getUsername());
				User uentity   = urepo.getByUsername(user.getUsername());
				if(uentity != null)
				{
					if(passwordencoder.matches(user.getPassword(),uentity.getPassword()))
					{
						System.out.println("in /checklogin  admin ");
						//session.setAttribute("user", uentity.getUsername());
						return "redirect:/adminreport";
					}
				}
				
			}
			else
			{
				User uentity   = urepo.getByUsername(user.getUsername());
				if(uentity != null)
				{
					if(passwordencoder.matches(user.getPassword(),uentity.getPassword()))
					{
						//session.setAttribute("user", uentity.getUsername());
						return "redirect:/attendencehome";
					}
				}
			}
			
		}
		return "index";
	}
	
	@GetMapping("/attendencehome")
	public String attendencehomePage(Model model,@AuthenticationPrincipal UserDetails userDetails,Principal principal)
	{
		System.out.println("in /attendencehome");
//		if(session.getAttribute("user") == null ||session.getAttribute("user").equals("")) 
//		{
//			return "redirect:/";
//		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.out.println(" principal : "+principal.getName()+"  ::: "+currentPrincipalName);
		System.out.println(userDetails.getAuthorities());
		System.out.println(userDetails.getUsername());
		User user = urepo.getByUsername(userDetails.getUsername());
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatdate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DateTimeFormatter myFormattime = DateTimeFormatter.ofPattern("HH:mm:ss a");
		
		SignDetail singledetails = srepo.findFirstByUserIdOrderBySignindatetimeDesc((user.getId()));
		//SignDetail singledetails = srepo.findFirstByUserIdOrderBySignindatetimeDesc((id));
		
		if(singledetails != null)
		{
			if(singledetails.getSignoutdatetime()== null)
			{
				model.addAttribute("signstatus","1");
				model.addAttribute("currentDate",myDateObj.format(myFormatdate));
				model.addAttribute("currentTime",myDateObj.format(myFormattime));
			}
			else
			{		
				model.addAttribute("currentDate",myDateObj.format(myFormatdate));
				model.addAttribute("currentTime", myDateObj.format(myFormattime));
				model.addAttribute("signstatus","2");
			}
		}
		else 
		{
			model.addAttribute("currentDate",myDateObj.format(myFormatdate));
			model.addAttribute("currentTime", myDateObj.format(myFormattime));
			model.addAttribute("signstatus","2");
		}
		model.addAttribute("userId",user.getId());
		return "attendencehome";
	}
	
	@GetMapping("/signonoff")
	public String doSignonoff(@AuthenticationPrincipal UserDetails userDetails)
	{
		System.out.println("in /signonoff");
//		if(session.getAttribute("user") == null ||session.getAttribute("user").equals("")) 
//		{
//			return "redirect:/";
//		}
		User user = urepo.getByUsername(userDetails.getUsername());
		System.out.println(userDetails.getUsername());
		SignDetail singledetails = srepo.findFirstByUserIdOrderBySignindatetimeDesc((user.getId()));
		
		if(singledetails != null) 
		{
			
			if(singledetails.getSignoutdatetime()!= null )
			{
				SignDetail sd = new SignDetail();
				sd.setUserId(user.getId());
				sd.setSignindatetime(LocalDateTime.now());
				srepo.save(sd);
			}
			else
			{	
				singledetails.setSignoutdatetime(LocalDateTime.now());
				srepo.save(singledetails);	
			}
		
			return "redirect:/attendencehome";
		}	
			SignDetail sd = new SignDetail();
			sd.setUserId(user.getId());
			sd.setSignindatetime(LocalDateTime.now());
			srepo.save(sd);
			
		return "redirect:/attendencehome";
	}
	
	
	@GetMapping("/attendancereport")
	public String getAttendencereport(Model model,@AuthenticationPrincipal UserDetails userDetails)
	{
//		if(session.getAttribute("user") == null ||session.getAttribute("user").equals("")) 
//		{
//			return "redirect:/";
//		}
		User user = urepo.getByUsername(userDetails.getUsername());
		System.out.println(userDetails.getUsername());
		List<SignDetail> sdetails = srepo.getByUserIdOrderBySignindatetimeDesc((user.getId()));
		if(sdetails != null) {
			sdetails.forEach((u)->{System.out.println(u.toString());});
			DateTimeFormatter myFormatdate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			DateTimeFormatter myFormattime = DateTimeFormatter.ofPattern("HH:mm:ss a");
			ArrayList<Signinoff> lists = new ArrayList<>();
			previousSignDate = "";
			sdetails.forEach((s)->{
				String Signintime = "Absent";
				String Signouttime = "Absent";
				String SignDate = "";
				
				if(s.getSignindatetime() != null)
				{
					SignDate = s.getSignindatetime().format(myFormatdate);
				}
				if(s.getSignindatetime() != null)
				{
					Signintime = s.getSignindatetime().format(myFormattime);
				}
				if(s.getSignoutdatetime() != null)
				{
					Signouttime = s.getSignoutdatetime().format(myFormattime);
				}
				lists.add(new Signinoff(s.getId(), s.getUserId(),
						previousSignDate.equals("")|| !previousSignDate.equals(SignDate)? s.getSignindatetime().format(myFormatdate):"", 
						Signintime, 
						Signouttime,!previousSignDate.equals("")&& !previousSignDate.equals(SignDate)? 1:2));
				
				previousSignDate = SignDate;
				
			});
			model.addAttribute("lists", lists);
			
		}
		model.addAttribute("userid",user.getId());
		return "attendancereport";
	}
	@GetMapping("/adminreport")
	public String getAdminreport(Model model,@AuthenticationPrincipal UserDetails userDetails) 
	{
//		if(session.getAttribute("user") == null ||session.getAttribute("user").equals("")) 
//		{
//			return "redirect:/";
//		}
		System.out.println("in /adminreport  admin ");
		List<User> lists = urepo.getByUsersWhereRoleisNotAdmin(	);
		//List<User> lists = urepo.findAll();
		if(lists != null)
		{
			model.addAttribute("lists", lists);
		}
		return "adminreport";
	}

	  
	
	
	@PostMapping("/logout")
	public String getdologout()
	{
		
		return "index";
	}
	@GetMapping("/attendancereportadmin/{id}")
	public String getAttendencereportadmin(@PathVariable int id ,Model model,@AuthenticationPrincipal UserDetails userDetails)
	{
//		if(session.getAttribute("user") == null ||session.getAttribute("user").equals("")) 
//		{
//			return "redirect:/";
//		}
		User user = urepo.getByUsername(userDetails.getUsername());
		System.out.println(userDetails.getUsername());
		List<SignDetail> sdetails = srepo.getByUserIdOrderBySignindatetimeDesc(id);
		if(sdetails != null) {
			sdetails.forEach((u)->{System.out.println(u.toString());});
			DateTimeFormatter myFormatdate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			DateTimeFormatter myFormattime = DateTimeFormatter.ofPattern("HH:mm:ss a");
			ArrayList<Signinoff> lists = new ArrayList<>();
			previousSignDate = "";
			sdetails.forEach((s)->{
				String Signintime = "Absent";
				String Signouttime = "Absent";
				String SignDate = "";
				
				if(s.getSignindatetime() != null)
				{
					SignDate = s.getSignindatetime().format(myFormatdate);
				}
				if(s.getSignindatetime() != null)
				{
					Signintime = s.getSignindatetime().format(myFormattime);
				}
				if(s.getSignoutdatetime() != null)
				{
					Signouttime = s.getSignoutdatetime().format(myFormattime);
				}
				lists.add(new Signinoff(s.getId(), s.getUserId(),
						previousSignDate.equals("")|| !previousSignDate.equals(SignDate)? s.getSignindatetime().format(myFormatdate):"", 
						Signintime, 
						Signouttime,!previousSignDate.equals("")&& !previousSignDate.equals(SignDate)? 1:2));
				
				previousSignDate = SignDate;
				
			});
			model.addAttribute("lists", lists);
			
		}
		model.addAttribute("userid",user.getId());
		return "attendancereport";
	}
	
	@GetMapping("/loginsuccess")
	public String onSuccesslogin(@AuthenticationPrincipal UserDetails userDetails)
	{
		if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("admin")))
		{
			return "redirect:/adminreport";
		}
		return "redirect:/attendencehome";
		
		
	}
		
	
}
