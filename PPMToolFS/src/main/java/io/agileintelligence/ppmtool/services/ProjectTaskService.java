package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.domain.Backlog;
import io.agileintelligence.ppmtool.domain.ProjectTask;
import io.agileintelligence.ppmtool.repositories.BacklogRepository;
import io.agileintelligence.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        //PTs to be added to a specific project, project != null,BL exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        //set the bl to pt
        projectTask.setBacklog(backlog);
        //the project seq should be like this : IDPRO-1 IDPRO-2 etc'
        Integer BacklogSequence = backlog.getPTSequence();
        //Update the BL seq
        BacklogSequence++;

        backlog.setPTSequence(BacklogSequence);
        //add seq to project task
        projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);


        //Initial priority when priority null
        if (projectTask.getPriority() == null) {
            projectTask.setPriority(3);
        }
        //Initial status when status is null
        if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
            projectTask.setStatus("TO_DO");
        }
        return projectTaskRepository.save(projectTask);
    }

public Iterable<ProjectTask>findbacklogById(String id){
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
}
}
