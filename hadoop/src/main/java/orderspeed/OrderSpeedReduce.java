package orderspeed;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

//输出 Key：司机，Value：时间，距离，平均速度，最大速度，
public class OrderSpeedReduce extends Reducer<Text, Text, Text, Text> {
    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
     /*   StringBuilder speeds = new StringBuilder();
        for (Text speed : values) {
            speeds.append(speed.toString()).append(",");
        }*/
        int timeTotal = 0;
        double distanceTotal = 0;
        double speedMax = 0;
        for (Text t : values) {
            String[] data = t.toString().split(",");
            if (data.length == 4) {
                timeTotal += Integer.parseInt(data[0]);
                distanceTotal += Double.parseDouble(data[1]);
                double thisSpeed = Double.parseDouble(data[3]);
                if (thisSpeed > speedMax) {
                    speedMax = thisSpeed;
                }
            }
        }
        double avgSpeed = distanceTotal / timeTotal;
        Text result = new Text(String.format("%d,%f,%f,%f", timeTotal, distanceTotal, avgSpeed, speedMax));
        context.write(key, result);
    }

}
