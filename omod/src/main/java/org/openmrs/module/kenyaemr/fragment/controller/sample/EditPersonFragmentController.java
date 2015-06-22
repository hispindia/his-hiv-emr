package org.openmrs.module.kenyaemr.fragment.controller.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.module.kenyaui.form.AbstractWebForm;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.BindParams;
import org.openmrs.ui.framework.annotation.MethodParam;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class EditPersonFragmentController {
	Log log = LogFactory.getLog(EditPersonFragmentController.class);

	
	@RequestMapping(method=RequestMethod.GET)
	public void controller(UiUtils ui,
	                         PageModel model) {
		model.addAttribute("command", new PersonForm());


	}
	
	
	public PersonForm newEditPersonForm(@RequestParam(value = "personId", required = false) Person person) {
		return new PersonForm(); // For creating patient and person from scratch
	}

	
	@RequestMapping(method=RequestMethod.POST)
	public SimpleObject savePerson(@MethodParam("newEditPersonForm") @BindParams PersonForm form, UiUtils ui) {
		System.out.println("Entering savePerson");
		Person person = form.save();
		
		
		return null;
		
	}
	
	/**
	 * The form command object for editing patients
	 */
	public class PersonForm extends AbstractWebForm {
		private Person original;
		public Person getOriginal() {
			return original;
		}
		public void setOriginal(Person original) {
			this.original = original;
		}
		private String firstName;
		private String lastName;
		private String middleName;
		private Integer age;
		
		public PersonForm(){
		}
		
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getMiddleName() {
			return middleName;
		}
		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		@Override
		public void validate(Object arg0, Errors arg1) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public Person save() {
			
			log.info("Save person");
			log.info("firstName: "+firstName);
			log.info("lastName: "+lastName);
			log.info("middleName: "+middleName);
			log.info("age: "+age);
			
			return null;
		}
	}
}
