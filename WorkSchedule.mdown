Week 3:
(Learn Android Studio)

Logging In and creating task lists
- Create a sign up interface (complete)
- Integrate Parse(?) for back-end (complete)
- Allow user to create a task list (complete)
- Allow user to invite other users into a task list (void)
- Create log in interface (complete)
- Link task lists to task list view (complete)


Week 4:


Logging In and creating task lists (continued)
- Create a sign up interface (complete)
- Integrate Parse(?) for back-end (complete)
- Allow user to create a task list (private) (complete)
- Allow user to invite other users into a task list (for now this is offline) (complete)
- Create log in interface (complete)
- Link task lists to task list view (complete)





Week 5:

Logging In and creating task lists (continued)
- Create a sign up interface (complete)
- Integrate Parse(?) for back-end (complete)
- Allow user to create a task list (private) (complete)
- Allow user to invite other users into a task list (offline right now) (complete)
- Create log in interface (complete)
- Link task lists to task list view (complete)

Week 6:
Create, view and assign tasks to members of the task list (continued)
- Create an interface for viewing the members of a task list (complete)
- Add functionality to create a new task (complete)
    - task can have name, description, (repeating... daily, weekly etc), initial assignment, difficulty/time (complete)
- Create an interface for viewing the tasks within the task list (complete)
- View your own tasks vs. view all tasks, separate lists (complete)
- Add functionality to assign a task to a member (complete)



Week 7:
Handle task assignment and completion (assignment continued)
 - Allow users to complete tasks (in progress)

NOTE: Users can complete tasks, but at this point it is functionally equivalent to deleting a task... when we implement the log interface, completed tasks will go in the log
 - Manage task assignment between users, drop assignment (in progress)
NOTE: Users can change or drop assignment of task, but the dropping of task is not yet intuitive... right now to dorp a task, a user goes to the edit task page and just doesn't assign it to someone... 
    We will make an actual button to drop a task soon
 - Send notifications to users when task is assigned, completed (implement delete/edit tasks) (stretch goal now)

Status Update:
  We're almost finished with the main infrastructure of our task management app. We have made it possible for users to create groups of users to share a task list, for users to create tasks
within those groups, for users to assign tasks, and for users to edit/delete/complete tasks. Parse is responding to creates and deletes fine, though we plan to add more unit tests next week.
Although task assignment is working, it's a bit confusing to users because the task create/edit page does not currently display the assigned user's name' after someone clicks on a user's name.
We will get this fixed next week. We will also allow users to view task details in their "Your task list" page. Right now, viewing specific task details is only supported in the whole group's task 
list page. Additionally, we have gotten edit to be functional, but when users go to edit a task, the initial state of the task is blank and users will have to repopulate all the task info even if they
didn't want to change all of it. We will get it so that the task editor automatically populates its fields with its current fields.
The randomization for next week should mostly be coming up with an algorithm, as the assignment functionality is already in place. Meanwhile, the user interface is shaping up, as we are going back 
the the app views and cleaning up the look and feel of the front end.


Week 8:
Enable randomization of task assignment
 - Design a fair randomization algorithm (in progress)
    - using the set difficulties of tasks (in progress)
    - we need a default difficulty if users don't set a difficulty (complete)
 - Implement it as an option for task assignment (complete)
    - there should be a button on the task list screen that will void all assignments and reassign randomly (complete)

Status update:
 We updated the UI for a few more views and cleaned up some of the bugs from last week. The main purpose of this week was to implement a fair randomization algorithm, however we ran into trouble with Parse, since we could not modify other users who were not logged in. We had trouble fixing this problem, and thus we were not able to implement a fair system, though we were able to implement a purely random system. We have come up with a workaround on paper, and we are confident we will be able to implement the changes in code in the upcoming week. We also fixed a few design violations in edit/create task, as well a bug in the delete task.
Bugs to fix: 
- "Your Tasks" displays all of your tasks, we only want to display the tasks that exist in the current task list context
- Randomization needs to be fair
- Android device back button is messing up our navigation flow

Week 9:
Enable locking of task assignment
 - Add a locking button
 - Once locking is activated, prevent randomziation function from activating

View log of tasks (completion, assignment, creation)
- Store task completion, assignment, and creation data
- Display data in log

