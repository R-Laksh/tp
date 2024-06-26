package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.student.Student;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' ClassMonitor file path.
     */
    Path getClassMonitorFilePath();

    /**
     * Sets the user prefs' ClassMonitor file path.
     */
    void setClassMonitorFilePath(Path classMonitorFilePath);

    /**
     * Replaces ClassMonitor data with the data in {@code classMonitor}.
     */
    void setClassMonitor(ReadOnlyClassMonitor classMonitor);

    /** Returns the ClassMonitor */
    ReadOnlyClassMonitor getClassMonitor();

    /**
     * Returns true if a student with the same identity as {@code student} exists in ClassMonitor.
     */
    boolean hasStudent(Student student);

    /**
     * Deletes the given student.
     * The student must exist in ClassMonitor.
     */
    void deleteStudent(Student target);

    /**
     * Adds the given student.
     * {@code student} must not already exist in ClassMonitor.
     */
    void addStudent(Student student);

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in ClassMonitor.
     * The student identity of {@code editedStudent} must not be the same as another existing student in the address
     * book.
     */
    void setStudent(Student target, Student editedStudent);

    /** Returns an unmodifiable view of the student list */
    ObservableList<Student> getCorrectStudentList();

    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getFilteredStudentList();

    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getSortedStudentList();

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);
    /**
     * Updates the sorted state of the sorted person list based off a given field
     * either in ascending or descending order
     */
    void updateSortedStudentListByField(String field, boolean isAscending);






}
