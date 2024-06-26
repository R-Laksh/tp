package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.student.Student;

/**
 * Represents the in-memory model of ClassMonitor data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ClassMonitor classMonitor;
    private final UserPrefs userPrefs;
    private final SortedList<Student> sortedStudents;
    private final FilteredList<Student> filteredStudents;

    /**
     * Initializes a ModelManager with the given classMonitor and userPrefs.
     */
    public ModelManager(ReadOnlyClassMonitor classMonitor, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(classMonitor, userPrefs);

        logger.fine("Initializing with ClassMonitor: " + classMonitor + " and user prefs " + userPrefs);

        this.classMonitor = new ClassMonitor(classMonitor);
        this.userPrefs = new UserPrefs(userPrefs);
        //initially both lists would be the same as the default
        filteredStudents = new FilteredList<>(this.classMonitor.getStudentList());
        sortedStudents = new SortedList<>(filteredStudents);
    }

    public ModelManager() {
        this(new ClassMonitor(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getClassMonitorFilePath() {
        return userPrefs.getClassMonitorFilePath();
    }

    @Override
    public void setClassMonitorFilePath(Path classMonitorFilePath) {
        requireNonNull(classMonitorFilePath);
        userPrefs.setClassMonitorFilePath(classMonitorFilePath);
    }

    //=========== ClassMonitor ================================================================================

    @Override
    public void setClassMonitor(ReadOnlyClassMonitor classMonitor) {
        this.classMonitor.resetData(classMonitor);
    }

    @Override
    public ReadOnlyClassMonitor getClassMonitor() {
        return classMonitor;
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return classMonitor.hasStudent(student);
    }

    @Override
    public void deleteStudent(Student target) {
        updateSortedStudentListByField("name", true);
        classMonitor.removeStudent(target);
    }

    @Override
    public void addStudent(Student student) {
        updateSortedStudentListByField("name", true);
        classMonitor.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);
        classMonitor.setStudent(target, editedStudent);
        updateSortedStudentListByField("name", true);
    }

    //=========== Filtered & Sorted Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code versionedClassMonitor}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudents;
    }

    @Override
    public ObservableList<Student> getSortedStudentList() {
        return sortedStudents;
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }
    @Override
    public void updateSortedStudentListByField(String field, boolean isAscending) {
        Comparator<Student> comparator;
        switch (field.toLowerCase()) {
        case "email":
            comparator = Comparator.comparing(Student::getEmail);
            break;
        case "major":
            comparator = Comparator.comparing(Student::getMajor);
            break;
        case "name":
            comparator = Comparator.comparing(Student::getName);
            break;
        case "phone":
            comparator = Comparator.comparing(Student::getPhone);
            break;
        case "star":
            comparator = Comparator.comparing(Student::getStar);
            break;
        case "bolt":
            comparator = Comparator.comparing(Student::getBolt);
            break;
        default:
            throw new IllegalArgumentException("Invalid field for sorting: " + field);
        }
        if (!isAscending) {
            comparator = comparator.reversed();
        }
        comparator = comparator.thenComparing(Student::getName);
        sortedStudents.setComparator(comparator);
    }
    @Override
    public ObservableList<Student> getCorrectStudentList() {
        return sortedStudents;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return classMonitor.equals(otherModelManager.classMonitor)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredStudents.equals(otherModelManager.filteredStudents);
    }

}
