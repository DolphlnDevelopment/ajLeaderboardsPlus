package us.ajg0702.leaderboards.placeholders.placeholders.lb;

import org.bukkit.OfflinePlayer;
import us.ajg0702.leaderboards.LeaderboardPlugin;
import us.ajg0702.leaderboards.boards.StatEntry;
import us.ajg0702.leaderboards.boards.TimedType;
import us.ajg0702.leaderboards.placeholders.Placeholder;

import java.util.Locale;
import java.util.regex.Matcher;

public class DisplayName extends Placeholder {
    public DisplayName(LeaderboardPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getRegex() {
        return "lb_(.*)_([1-9][0-9]*)_(.*)_displayname";
    }

    @Override
    public String parse(Matcher matcher, OfflinePlayer p) {
        String board = matcher.group(1);
        String typeRaw = matcher.group(3).toUpperCase(Locale.ROOT);
        TimedType type = TimedType.of(typeRaw);
        if(type == null) {
            return "Invalid TimedType '" + typeRaw + "'";
        }
        StatEntry r;
        if (board.endsWith("REVERSE")) {
            board = board.substring(0, board.length() - 7);
            type = TimedType.ALLTIME;
            r = plugin.getTopManager().getReversedStat(Integer.parseInt(matcher.group(2)), board, type);
        } else {
            r = plugin.getTopManager().getStat(Integer.parseInt(matcher.group(2)), board, type);
        }
        return r.getPlayerDisplayName();
    }
}
