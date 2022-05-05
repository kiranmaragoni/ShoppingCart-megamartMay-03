package com.shoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.entity.Cart;
import com.shoppingcart.entity.Item;
import com.shoppingcart.service.ShoppingCartService;

@RestController
@RequestMapping(value = "users")
public class CartController {

	@Autowired
	private ShoppingCartService shoppingCartService;

	// Get cart
	@GetMapping(value = "/{accountId}/cart")
	public ResponseEntity<?> getCart(@PathVariable int accountId){
		try {
			shoppingCartService.checkUser(accountId);
			return new ResponseEntity<Cart>(shoppingCartService.getCart(accountId),HttpStatus.FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
		
	}

	// Display products in cart
	@GetMapping(value = "{accountId}/cart/products")
	public ResponseEntity<?> getProductsInCart(@PathVariable("accountId") int accountId){
		try {
			shoppingCartService.checkUser(accountId);
			List<Item> itemList = shoppingCartService.getProductsInCart(accountId);
			return new ResponseEntity<List<Item>>(itemList,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		}
	}

	// Add product in cart
	@PostMapping(value = "/{accountId}/cart/products")
	public ResponseEntity<?> addProductInCart(
			@PathVariable("accountId") int accountId, 
			@RequestParam int productId){
		try {
			shoppingCartService.checkUser(accountId);
			shoppingCartService.checkProduct(productId);
			Cart cart = shoppingCartService.addProductInCart(accountId, productId);
			return new ResponseEntity<Cart>(cart, HttpStatus.ACCEPTED);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}
	}

	// Update Quantity of a Product in cart
	@PutMapping(value = "/{accountId}/cart/products/{productId}")
	public ResponseEntity<?> updateProductQuantityInCart(
			@PathVariable("accountId") int accountId,
			@PathVariable("productId") int productId,
			@RequestParam int quantity){
		try {
			shoppingCartService.checkUser(accountId);
			shoppingCartService.checkProduct(productId);
			Cart cart = shoppingCartService.updateProductQuantityInCart(accountId, productId, quantity);
			return new ResponseEntity<Cart>(cart, HttpStatus.ACCEPTED);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}
	}

	// Delete Product from cart
	@DeleteMapping(value = "/{accountId}/cart/products")
	public ResponseEntity<?> removeProductFromCart(
			@PathVariable("accountId") int accountId,
			@RequestParam int productId){
		try {
			shoppingCartService.checkUser(accountId);
			shoppingCartService.checkProduct(productId);
			Cart cart = shoppingCartService.removeProductFromCart(accountId, productId);
			return new ResponseEntity<Cart>(cart, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}	
	}
	@DeleteMapping(value = "/{accountId}/cart")
	public ResponseEntity<?> removeAllProductsFromCart(@PathVariable("accountId") int accountId){
		try {
			shoppingCartService.checkUser(accountId);
			Cart cart = shoppingCartService.removeAllProductsFromCart(accountId);
			return new ResponseEntity<Cart>(cart, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
