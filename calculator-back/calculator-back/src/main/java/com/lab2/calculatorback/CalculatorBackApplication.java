package com.lab2.calculatorback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
@SpringBootApplication
@RestController
@CrossOrigin(origins = "*")
public class CalculatorBackApplication {
	public static void main(String[] args) {
		SpringApplication.run(CalculatorBackApplication.class, args);
	}
	@PostMapping
	public double calculate(@RequestBody ArrayList<String> data) {
		String a = data.get(0);
		String b = data.get(1);
		String op = data.get(2);
		double result=0;
		if ("".equals(b)) {
			return Double.valueOf(a);
		} else {
			switch (op) {
				case "+":
					result = Double.valueOf(a) + Double.valueOf(b);
					break;
				case "-":
					result = Double.valueOf(a) - Double.valueOf(b);
					break;
				case "*":
					result = Double.valueOf(a) * Double.valueOf(b);
					break;
				case "/":
					result = Double.valueOf(a) / Double.valueOf(b);
					break;
				default:
					result=Double.valueOf(a);
			}
			return result;
		}
	}
	@PostMapping("/single")
	public Double SingleOperation(@RequestBody String []values){
		Double val= Double.valueOf(values[0]);
		String operation=values[1];
	 switch(operation){
		 case "percentage":
			 val=val/100;
			 break;
		 case "root":
			 val=Math.sqrt(val);
			 break;
		 case "square":
			 val=Math.pow(val,2);
			 break;
		 case "reciprocal":
			 val=1/val;
			 break;
	 }
	 return val;
	}
}