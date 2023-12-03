package us.ajg0702.leaderboards.placeholders.placeholders.lb;

import org.bukkit.OfflinePlayer;
import us.ajg0702.leaderboards.LeaderboardPlugin;
import us.ajg0702.leaderboards.boards.StatEntry;
import us.ajg0702.leaderboards.boards.TimedType;
import us.ajg0702.leaderboards.placeholders.Placeholder;

import java.util.Locale;
import java.util.regex.Matcher;

@Deprecated
public class Time extends Placeholder {
    public Time(LeaderboardPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getRegex() {
        return "lb_(.*)_([1-9][0-9]*)_(.*)_time";
    }

    @Override
    public String parse(Matcher matcher, OfflinePlayer p) {
        plugin.timePlaceholderUsed();
        String board = matcher.group(1);
        String typeRaw = matcher.group(3).toUpperCase(Locale.ROOT);
        StatEntry r;
        if (board.endsWith("REVERSE")) {
            board = board.substring(0, board.length() - 7);
            r = plugin.getTopManager().getReversedStat(Integer.parseInt(matcher.group(2)), board, TimedType.ALLTIME);
        } else {
            r = plugin.getTopManager().getStat(Integer.parseInt(matcher.group(2)), board, TimedType.valueOf(typeRaw));
        }
        return r.getTime();
    }
}
