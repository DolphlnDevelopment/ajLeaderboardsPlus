package us.ajg0702.leaderboards.placeholders.placeholders.player;

import org.bukkit.OfflinePlayer;
import us.ajg0702.leaderboards.LeaderboardPlugin;
import us.ajg0702.leaderboards.boards.StatEntry;
import us.ajg0702.leaderboards.boards.TimedType;
import us.ajg0702.leaderboards.placeholders.Placeholder;

import java.util.Locale;
import java.util.regex.Matcher;

public class PlayerPosition extends Placeholder {
    public PlayerPosition(LeaderboardPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getRegex() {
        return "position_(.*)_(.*)";
    }

    @Override
    public String parse(Matcher matcher, OfflinePlayer p) {
        String board = matcher.group(1);
        String typeRaw = matcher.group(2).toUpperCase(Locale.ROOT);
        TimedType type = TimedType.of(typeRaw);
        if(type == null) {
            return "Invalid TimedType '" + typeRaw + "'";
        }
        boolean reverse = board.endsWith("REVERSE");

        if (reverse) {
            board = board.substring(0, board.length() - 7);
            type = TimedType.ALLTIME;
        }

        int position = plugin.getTopManager().getStatEntry(p, board, type).getPosition();
        if(position == -3) return "BDNE";
        if(position == -2) return plugin.getMessages().getString("loading.position");

        if (reverse) {
            int size = plugin.getTopManager().getBoardSize(board);
            position = size - position + 1;
        }

        return StatEntry.addCommas(position);
    }
}
