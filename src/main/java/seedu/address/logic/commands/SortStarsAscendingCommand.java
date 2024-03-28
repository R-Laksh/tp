package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;


/**
 * Sorts all students in the address book based on their number of starts and lists to the user.
 */
public class SortStarsAscendingCommand extends Command {

    public static final String COMMAND_WORD = "sortStarsAscending";
    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateSortedPersonListSortStarsAscending();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortStarsAscendingCommand)) {
            return false;
        }
        return true;
    }

}