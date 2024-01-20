package com.FastKart.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FastKart.Dao.cartDao;
import com.FastKart.Dao.userDao;
import com.FastKart.Repository.CartRepository;
import com.FastKart.entities.Cart;

@Controller
public class cartController {

	@Autowired
	private cartDao cdao;
	
	@Autowired
	private userDao udao;
	
	@Autowired
	private cartDao cartdao;
	
	@Autowired
	private CartRepository cartRepository;

	 @PostMapping("/addToCart")
	    public String  addToCart(@ModelAttribute Cart cart, @RequestParam("pid") int pid,@RequestParam("quntity") int quntity, Principal principal) {
	   
		
		
	 if (principal != null && udao.isUserLoggedIn(principal)) {
		 cdao.addToCart(cart, pid, quntity, principal);
         return "redirect:/viewCart";
	    } else {
	        // Redirect to the login page
	        return "redirect:/login";	
	    }
}
	 
@GetMapping("/deleteCart/{id}")
public String deleteCart(@PathVariable("id") int id , Model m) {
	
	cartdao.deleteCart(id);
	return "redirect:/viewCart";
}


@PostMapping("/updateCart")
public String updateCart(@RequestParam("id") int id, @RequestParam("quntity") int quntity) {
	
	cartdao.updateCart(id, quntity);
	
	return "redirect:/viewCart"; 
	
}


 @PostMapping("/discount") 
 public String getDiscount( @RequestParam("couponId") String couponCode, Principal principal, Model m) {
 
 List<Cart> cartList = cartdao.viewCart(principal);
 
 int totalOfCart = cartdao.getTotalOfCart(cartList);
 m.addAttribute("totalOfCart", totalOfCart);
 
 int shippingTotal = cartdao.getShippingTotal(cartList);
 m.addAttribute("shippingTotal", shippingTotal);
 
 int totalWithShipping = cartdao.getTotalWithShipping(cartList);
 m.addAttribute("totalWithShipping", totalWithShipping);
 
 
 int discount = cartdao.getDiscount(couponCode, principal);
 m.addAttribute("discount", discount);
 
 int grandTotal = totalWithShipping - discount; m.addAttribute("grandTotal",
 grandTotal);
 
 return "redirect:/viewCart"; }
 

	 
	 
	
}
