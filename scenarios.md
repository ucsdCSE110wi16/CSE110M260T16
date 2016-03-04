# Scenarios Tested with Espresso

1. `SignInTest`

- Given I already have an account
- When I log in
- Then I will see all the task lists that I am a part of

2. `CreateTaskTest`

- Given I already am a member of a task list, and other members have created tasks
When I press one of my task lists on the main menu
Then I will enter a private room, showing the members of that task list

- Given that I am on the main menu screen
When I finish creating or adding a task
Then I will go back to the main menu

- Given I already am a member of a task list
When I finish creating a task
Then it will be added to the menu

3. `CreateListTest`

- Given that I already have an account
When I login and choose to create a new task list
Then I will create a new task list that

4. `MembersTest`

- Given I already am a member of a task list
When I press the button to view the members in this task list
Then I will enter a screen displaying the users that belong to the current task list

5. `UnitTest`

- Given I am attempting to sign up for an account
When I enter a password that is too short
Then I will be rejected from creating an account

- Given I am attempting to sign up for an account
When I enter an invalid email
Then I will be rejected from creating an account

# Scenarios

1. Given I am already logged in
Then when I open the app
I will go straight to the main menu view

2. Given I do not have an account
When when I press sign up
Then I can fill out my sign up info
When I finish
Then I can see my main menu page

3. Given that I have tasks assigned to me
When I view my shared tasklist 
Then I should see just the tasks I am assigned to
When I click on "see all tasks" 
Then I should see all the tasks in the tasklist

4. Given that tasks have a deadline
When I view a task on the list
Then I should be able to see the deadline next to it

5. Given that I have a task assigned to me
When I click on that task in the list
Then I should be able to view the details of that task

6. Given that I delete a task
When I go back to the task list page
Then I should not see the task I just deleted

7. Given that I edit a task
When I click the task again
Then I should see the updated version

8. Given that I assign a task to my friend
When my friend view his/her tasks
Then they should see the task that I assigned to them

9. Given that I have a task assigned to me
When I don't complete the task by the deadline
Then I will get notified

10. Given that I have received more easy or hard tasks than average
When task assignments are randomized
Then I expect to receive a hard or easy task, respectively, next time

11. Given that task assignments are randomized
When several dozen tasks have been assigned and completed
Then the average difficulty of the tasks all members have completed should be roughly equal

12. Given that for a certain task the assignment is locked
When I assign that task to a roommate
Then they can't back out of completing the task

13. Given that task assignment can be randomized
When I lock a task to a member
Then that task locks to that member regardless of randomization

14. Given that I lock assignment
When I or another user tries to randomize assignment
Then they will fail to make assignment changes
When another user tries to unlock assignment
Then they will fail to unlock the assignment
When I try to unlock assignment
Then assignment will be open to randomization and locking once more

15. Given that there is a log of tasks and tasks have been completed previously
When I scroll down the log
Then I can view a list of tasks that were assigned and completed

16. Given that there is a log of tasks and tasks have been completed previously
When I scroll down the log
Then the timeline of tasks displayed should be in reverse chronological order

17. Given that I complete a task
When I view the log
Then I should be able to see the task I just completed

18. Given that I delete a task
When I view the log
Then I should not be able to see the task I just deleted
