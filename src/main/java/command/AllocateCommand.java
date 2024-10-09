package command;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import allocator.Allocator;
import studentlist.StudentList;
import ui.UI;

public class AllocateCommand extends Command {
    private Allocator allocator;
    private UI ui;
    private ExecutorService executor = Executors.newFixedThreadPool(2); // Create a thread pool

    public AllocateCommand(StudentList studentList, UI ui) {
        super(studentList);
        this.ui = ui;
        this.allocator = new Allocator(studentList);
    }

    @Override
    public void run() {
        try {
            // Submit the allocation task
            Future<StudentList> allocationTask = executor.submit(() -> {
                return allocator.allocate();
            });

            // Submit the loading message task
            Future<?> loadingTask = executor.submit(() -> {
                while (!allocationTask.isDone()) {
                    this.ui.printAllocatingMessage();
                }
            });

            // Check periodically if the allocation is done
            while (!allocationTask.isDone()) {
                Thread.sleep(100); // Small delay to avoid busy-waiting
            }

            // Interrupt the loading task once allocation is done
            loadingTask.cancel(true);

            // Retrieve the result of the allocation task
            this.studentList.setStudentList(allocationTask.get());

            // Shut down the executor service
            executor.shutdown();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
