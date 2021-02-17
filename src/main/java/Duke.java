import java.util.ArrayList;
import java.util.Scanner;
import tasks.Task;
import tasks.Deadline;
import tasks.Event;
import tasks.ToDo;

public class Duke {
    public static void printList(ArrayList<Task> Tasks){
        for (int i=0; i<Tasks.size(); ++i){
            Task task = Tasks.get(i);
            Integer taskNumber = i+1;
            System.out.println(taskNumber + "." + task.toString());
        }
    }

    public static void printTaskAdded(ArrayList<Task> Tasks){
        printDashLine();
        System.out.println(" Got it. I've added this task:\n" + Tasks.get(Tasks.size()-1).toString());
        System.out.println("Now you have " + Tasks.size() + " tasks in the list.");
        printDashLine();
    }

    public static void printDashLine(){
        System.out.println("____________________________________________________________");
    }

    public static void validateInput(String[] words) throws DukeException {
        boolean isList = words[0].equals("list");
        boolean isDone = words[0].equals("done");
        boolean isTodo = words[0].equals("todo");
        boolean isDeadline = words[0].equals("deadline");
        boolean isEvent = words[0].equals("event");
        boolean isDelete = words[0].equals("delete");
        boolean invalidCommand = !(isList || isDone || isTodo || isDeadline || isEvent || isDelete);
        if(invalidCommand) {
            throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    public static void validateToDoInput(String[] words) throws DukeException {
        boolean invalidToDoInput = words.length == 1;
        if(invalidToDoInput) {
            throw new DukeException("☹ OOPS!!! The description of a todo cannot be empty.");
        }
    }

    public static void validateListInput(String[] words) throws DukeException {
        boolean invalidListInput = words.length > 1;
        if (invalidListInput) {
            throw new DukeException("☹ OOPS!!! The description of a list should be empty.");
        }
    }

    public static void main(String[] args) {
        ArrayList<Task> Tasks =  new ArrayList<Task>();
        String line;
        String INTRO_MESSAGE = " Hello! I'm Duke\n" + " What can I do for you?";
        String OUTRO_MESSAGE = "Bye. Hope to see you again soon!";
        Scanner Input = new Scanner(System.in);
        printDashLine();
        System.out.println(INTRO_MESSAGE);
        printDashLine();
        line = Input.nextLine();
        boolean inSystem = !line.equals("bye");
        while(inSystem){
            String[] words = line.split(" ");
            boolean isList = words[0].equals("list");
            boolean isDone = words[0].equals("done");
            boolean isTodo = words[0].equals("todo");
            boolean isDeadline = words[0].equals("deadline");
            boolean isEvent = words[0].equals("event");
            boolean isDelete = words[0].equals("delete");
            try {
                validateInput(words);
            } catch (Exception e){
                System.out.println(e);
                line = Input.nextLine();
                inSystem = !line.equals("bye");
                continue;
            }
            if(isList){
                try {
                    validateListInput(words);
                } catch (Exception e) {
                    System.out.println(e);
                    line = Input.nextLine();
                    inSystem = !line.equals("bye");
                    continue;
                }
                printDashLine();
                System.out.println("Here are the tasks in your list:");
                printList(Tasks);
                printDashLine();
            }
            else if(isDone) {
                int taskNumber = Integer.parseInt(words[1]) - 1;
                Tasks.get(taskNumber).taskComplete();
                printDashLine();
                System.out.println("Nice! I've marked this task as done:\n" + " " + Tasks.get(taskNumber).toString());
                printDashLine();
            }
            else if(isDelete) {
                int taskNumber = Integer.parseInt(words[1]) - 1;
                printDashLine();
                System.out.println("Noted. I've removed this task:\n" + Tasks.get(taskNumber).toString());
                Tasks.remove(taskNumber);
                System.out.println("Now you have " + Tasks.size() + " tasks in the list.");
                printDashLine();
            }
            else if (isTodo){
                try {
                    validateToDoInput(words);
                } catch (Exception e) {
                    System.out.println(e);
                    line = Input.nextLine();
                    inSystem = !line.equals("bye");
                    continue;
                }
                line = line.replace("todo ", "");
                ToDo toDo = new ToDo(line);
                Tasks.add(toDo);
                printTaskAdded(Tasks);
            }
            else if (isDeadline){
                line = line.replace("deadline ", "");
                words = line.split("/by ");
                Deadline deadline = new Deadline(words[0], words[1]);
                Tasks.add(deadline);
                printTaskAdded(Tasks);
            }
            else if (isEvent) {
                line = line.replace("event ", "");
                words = line.split("/at ");
                Event event = new Event(words[0], words[1]);
                Tasks.add(event);
                printTaskAdded(Tasks);
            }
            line = Input.nextLine();
            inSystem = !line.equals("bye");
        }
        printDashLine();
        System.out.println(OUTRO_MESSAGE);
        printDashLine();
    }
}