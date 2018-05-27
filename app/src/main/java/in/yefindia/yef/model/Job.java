package in.yefindia.yef.model;

/*
 * Created by Shashank Shinde.
 */
public class Job {
    private String jobId;
    private String jobTitle;
    private String companyName;
    private String location;
    private String experience;
    private String keySkills;
    private String description;
    private String salary;

    public Job() {}

    public Job(String jobId, String jobTitle, String companyName, String location, String experience, String keySkills, String description, String salary) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.location = location;
        this.experience = experience;
        this.keySkills = keySkills;
        this.description = description;
        this.salary = salary;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getKeySkills() {
        return keySkills;
    }

    public void setKeySkills(String keySkills) {
        this.keySkills = keySkills;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }


    @Override
    public String toString() {
        return "Job{" +
                "jobId='" + jobId + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", companyName='" + companyName + '\'' +
                ", location='" + location + '\'' +
                ", experience='" + experience + '\'' +
                ", keySkills='" + keySkills + '\'' +
                ", description='" + description + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }
}
