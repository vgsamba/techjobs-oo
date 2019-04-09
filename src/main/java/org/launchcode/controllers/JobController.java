package org.launchcode.controllers;

import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)

    public String index(Model model, @RequestParam int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job job = jobData.findById(id);
        model.addAttribute(job);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid,
        if (errors.hasErrors()) {
            model.addAttribute("jobForm",new JobForm()); // creating an object(jobForm) with JobForm() Constructor and
            return "new-job";           // sending it to view new-job.html..
        }
        //create a new Job and add it to the jobData data store. Then


        //unicorn.setName("java dev");

        String aName = jobForm.getName();

        Employer aEmployer = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location aLocation = jobData.getLocations().findById(jobForm.getLocationId());

        CoreCompetency aSkill = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());
        PositionType aPositionType=jobData.getPositionTypes().findById(jobForm.getPositionTypeId());


        Job unicorn= new Job(aName,aEmployer,aLocation,aPositionType,aSkill);
        // redirect to the job detail view for the new Job.
        //job = jobData.findByValue(jobForm.getName()).get(0);
        jobData.add(unicorn);
        model.addAttribute("job",unicorn);
        return "redirect:/job?id="+ unicorn.getId();

    }
}
