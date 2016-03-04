1. Given I already am a member of a task list
When I press one of my task lists on the main menu
Then I will enter a private room, showing the members of that task list, as long as the created tasks

2. Given that I am on the main menu screen
When I finish creating or adding a task
Then I will go back to the main menu

3. Given I already have an account
When I log in
Then I will see all the task lists that I am a part of

4. Given I am already logged in
Then when I open the app
I will go straight to the main menu view

5. Given I do not have an account
When when I press sign up
Then I can fill out my sign up info
When I finish
Then I can see my main menu page

6. Given that I have tasks assigned to me
When I view my shared tasklist 
Then I should see just the tasks I am assigned to
When I click on "see all tasks" 
Then I should see all the tasks in the tasklist

7. Given that tasks have a deadline
When I view a task on the list
Then I should be able to see the deadline next to it

8. Given that I have a task assigned to me
When I click on that task in the list
Then I should be able to view the details of that task

9. Given that I delete a task
When I go back to the task list page
Then I should not see the task I just deleted

10. Given that I edit a task
When I click the task again
Then I should see the updated version

11. Given that I assign a task to my friend
When my friend view his/her tasks
Then they should see the task that I assigned to them

12. Given that I have a task assigned to me
When I don't complete the task by the deadline
Then I will get notified

13. Given that I have received more easy or hard tasks than average
When task assignments are randomized
Then I expect to receive a hard or easy task, respectively, next time

14. Given that task assignments are randomized
When several dozen tasks have been assigned and completed
Then the average difficulty of the tasks all members have completed should be roughly equal

15. Given that for a certain task the assignment is locked
When I assign that task to a roommate
Then they can't back out of completing the task

16. Given that task assignment can be randomized
When I lock a task to a member
Then that task locks to that member regardless of randomization

17. Given that I lock assignment
When I or another user tries to randomize assignment
Then they will fail to make assignment changes
When another user tries to unlock assignment
Then they will fail to unlock the assignment
When I try to unlock assignment
Then assignment will be open to randomization and locking once more

18. Given that there is a log of tasks and tasks have been completed previously
When I scroll down the log
Then I can view a list of tasks that were assigned and completed

19. Given that there is a log of tasks and tasks have been completed previously
When I scroll down the log
Then the timeline of tasks displayed should be in reverse chronological order

20. Given that I complete a task
When I view the log
Then I should be able to see the task I just completed

21. Given that I delete a task
When I view the log
Then I should not be able to see the task I just deleted
