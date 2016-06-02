package io.typedef.cothroime.parser;

import com.google.protobuf.Parser;
import io.typedef.polar.model.Model.ExerciseData;
import io.typedef.polar.model.Model.LapSetData;
import io.typedef.polar.model.Model.PhysicalData;
import io.typedef.polar.model.Model.RouteData;
import io.typedef.polar.model.Model.SampleData;
import io.typedef.polar.model.Model.SessionData;
import io.typedef.polar.model.Model.StatisticData;
import io.typedef.polar.model.Model.ZoneData;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Parsers {

    private static final Map<Pattern, Parser> parsers = new HashMap<>();

    static {
        parsers.put(Pattern.compile("/\\d{8}/E/\\d{6}/TSESS.BPB$"), SessionData.PARSER);
        parsers.put(Pattern.compile("/\\d{8}/E/\\d{6}/PHYSDATA.BPB$"), PhysicalData.PARSER);
        parsers.put(Pattern.compile("/\\d{8}/E/\\d{6}/\\d{2}/BASE.BPB$"), ExerciseData.PARSER);
        parsers.put(Pattern.compile("/\\d{8}/E/\\d{6}/\\d{2}/ALAPS.BPB$"), LapSetData.PARSER);
        parsers.put(Pattern.compile("/\\d{8}/E/\\d{6}/\\d{2}/LAPS.BPB$"), LapSetData.PARSER);
        parsers.put(Pattern.compile("/\\d{8}/E/\\d{6}/\\d{2}/ROUTE.GZB$"), RouteData.PARSER);
        parsers.put(Pattern.compile("/\\d{8}/E/\\d{6}/\\d{2}/SAMPLES.GZB$"), SampleData.PARSER);
        parsers.put(Pattern.compile("/\\d{8}/E/\\d{6}/\\d{2}/STATS.BPB$"), StatisticData.PARSER);
        parsers.put(Pattern.compile("/\\d{8}/E/\\d{6}/\\d{2}/ZONES.BPB$"), ZoneData.PARSER);
    }

    public static Parser matching(String path) {
        for (Pattern pattern : parsers.keySet()) {
            if (pattern.matcher(path).find()) {
                return parsers.get(pattern);
            }
        }

        return null;
    }
}
