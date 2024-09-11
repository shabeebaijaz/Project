package in.pwskills.shabeeb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.pwskills.shabeeb.model.Employee;
import in.pwskills.shabeeb.service.IEmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	private IEmployeeService service;
	
	
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {
		System.out.println("EmployeeController.showFormForAdd()");
		model.addAttribute("employee", new Employee());
		return "/employees/employee-form";
	}
	
	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute Employee employee) {
		System.out.println("EmployeeController.saveEmployee()");
		service.saveEmployee(employee);
		return "redirect:/employees/list";
	}
	
	@RequestMapping("/")
	public String viewHomePage(Model model) {
	     System.out.println("EmployeeController.viewHomePage()");
	    return "redirect:/employees/list";
	}
	
	@GetMapping("/list")
	public String listEmployees(Model model) {
		System.out.println("EmployeeController.listEmployees()");
		List<Employee> employees = service.findAllEmployees();
		model.addAttribute("employees", employees);
		return "/employees/list-employees";
	}
	
	@RequestMapping("/showFormForUpdate/{eid}")
	public String showFormForUpdate(
		@PathVariable("eid") Integer id, Model model) {
		System.out.println("EmployeeController.showFormForUpdate()");
		Employee employees = service.findEmployeeById(id);
		model.addAttribute("employees", employees);
		return "/employees/edit-employee";
	}
	
	@GetMapping("/delete/{eid}")
	public String deleteEmployee(@PathVariable("eid") Integer id) {
		System.out.println("EmployeeController.deleteEmployee()");
		service.deleteById(id);
		return "redirect:/employees/list";
	}

}
