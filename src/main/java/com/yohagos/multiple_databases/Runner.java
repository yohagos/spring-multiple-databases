package com.yohagos.multiple_databases;

import com.yohagos.multiple_databases.ticket.entities.*;
import com.yohagos.multiple_databases.ticket.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class Runner {

    @Bean
    CommandLineRunner commandLineRunner(
            ProjectStatusRepository statusRepository,
            PriorityLevelRepository priorityLevelRepository,
            SkillsRepository skillsRepository,
            TicketStatusRepository ticketStatusRepository,
            TicketTypeRepository ticketTypeRepository
    ) {
        return args -> {
            var statusCount = statusRepository.count();
            var priorityCount = priorityLevelRepository.count();
            var skillsCount = skillsRepository.count();

            var ticketTypeCount = ticketTypeRepository.count();
            var ticketStatusCount = ticketStatusRepository.count();

            if (statusCount < 1) {
                statusRepository.saveAllAndFlush(
                        List.of(
                                new ProjectStatus("To Do", "#ebe134"),
                                new ProjectStatus("In Progress", "#43a326"),
                                new ProjectStatus("On Hold", "#26a395"),
                                new ProjectStatus("Completed", "#245cd4"),
                                new ProjectStatus("Delayed", "#7024d4"),
                                new ProjectStatus("Cancelled", "#d124d4"),
                                new ProjectStatus("Waiting for Approval", "#d49f24"),
                                new ProjectStatus("Testing", "#d42482"),
                                new ProjectStatus("Deployment", "#0a6619"),
                                new ProjectStatus("Archived", "#bd6e0d")
                        )
                );
            }

            if (priorityCount < 1) {
                priorityLevelRepository.saveAllAndFlush(
                        List.of(
                                new PriorityLevel("Low", "#0dbd71"),
                                new PriorityLevel("Medium", "#0dbd2a"),
                                new PriorityLevel("High", "#b7bd0d"),
                                new PriorityLevel("Urgent", "#ed8e11"),
                                new PriorityLevel("Blocker", "#7b11ed"),
                                new PriorityLevel("Nice-to-have", "#cc11ed"),
                                new PriorityLevel("Must-have", "#ed118a"),
                                new PriorityLevel("Optional", "#11cced"),
                                new PriorityLevel("Deferred", "#edbd11"),
                                new PriorityLevel("Critical", "#ed1148")
                        )
                );
            }

            if (skillsCount < 1) {
                var categoryProgramming = "Programming";
                var categoryFrameworks = "Frameworks";
                var categoryTesting = "Testing";
                var categoryDevops = "DevOps";
                var categoryCloud = "Cloud";
                var categoryMethods = "Management";
                var categoryDatabase = "Database";

                skillsRepository.saveAllAndFlush(
                        List.of(
                                new Skills("Java", categoryProgramming),
                                new Skills("Python", categoryProgramming),
                                new Skills("C++", categoryProgramming),
                                new Skills("C#", categoryProgramming),
                                new Skills("Go", categoryProgramming),
                                new Skills("Ruby", categoryProgramming),
                                new Skills("JavaScript", categoryProgramming),

                                new Skills("Spring Boot", categoryFrameworks),
                                new Skills("Node.js", categoryFrameworks),
                                new Skills(".NET", categoryFrameworks),
                                new Skills("React", categoryFrameworks),
                                new Skills("Vue", categoryFrameworks),
                                new Skills("Angular", categoryFrameworks),
                                new Skills("Flutter", categoryFrameworks),
                                new Skills("React Native", categoryFrameworks),

                                new Skills("Selenium", categoryTesting),
                                new Skills("Cypress", categoryTesting),
                                new Skills("JUnit", categoryTesting),
                                new Skills("pytest", categoryTesting),

                                new Skills("Docker", categoryDevops),
                                new Skills("Kubernetes", categoryDevops),
                                new Skills("Jenkins", categoryDevops),
                                new Skills("Terraform", categoryDevops),
                                new Skills("Prometheus", categoryDevops),
                                new Skills("Grafana", categoryDevops),
                                new Skills("GitLab CI/CD", categoryDevops),

                                new Skills("PostgreSQL", categoryDatabase),
                                new Skills("MariaDB", categoryDatabase),
                                new Skills("Cassandra", categoryDatabase),
                                new Skills("MongoDB", categoryDatabase),
                                new Skills("MySQL", categoryDatabase),
                                new Skills("Oracle", categoryDatabase),

                                new Skills("EC2", categoryCloud),
                                new Skills("Azure", categoryCloud),
                                new Skills("AWS", categoryCloud),
                                new Skills("Salesforce", categoryCloud),
                                new Skills("Google Cloud Functions", categoryCloud),

                                new Skills("Scrum", categoryMethods),
                                new Skills("Kanban", categoryMethods),
                                new Skills("Agile", categoryMethods),
                                new Skills("Prince2", categoryMethods)
                        )
                );
            }

            if (ticketStatusCount < 1) {
                ticketStatusRepository.saveAll(
                        List.of(
                                new TicketStatus("Open", "green"),
                                new TicketStatus("Developing", "yellow"),
                                new TicketStatus("Reviewing", "orange"),
                                new TicketStatus("Testing", "grey"),
                                new TicketStatus("Closed", "blue")
                        )
                );
            }

            if (ticketTypeCount < 1) {
                ticketTypeRepository.saveAll(
                        List.of(
                                new TicketType("Hotfix"),
                                new TicketType("Bug"),
                                new TicketType("Feature"),
                                new TicketType("Feature Request"),
                                new TicketType("Testing"),
                                new TicketType("Support")
                        )
                );
            }
        };
    }
}
