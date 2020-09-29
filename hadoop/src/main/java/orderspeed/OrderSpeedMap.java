package orderspeed;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//输出 Key：司机，订单，Value：这个订单的时间，距离，平均速度，最大速度，
public class OrderSpeedMap extends Mapper<Object, Text, Text, Text> {
    private static final double EARTH_RADIUS = 6378137;//赤道半径

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    //根据经纬度计算两点距离
    public static double getDistance(TimeCoord timeCoord1, TimeCoord timeCoord2) {
        double radLat1 = rad(timeCoord1.wei);
        double radLat2 = rad(timeCoord2.wei);
        double a = radLat1 - radLat2;
        double b = rad(timeCoord1.jing) - rad(timeCoord2.jing);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        return s;//单位米
    }

    //输出 Key：司机，Value：这个订单的时间，距离，平均速度，最大速度，
    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        //司机，订单    (Tab)时间，坐标；
        String[] data = value.toString().split("\t");

        if (data.length >= 2) {
            String driverOrder = data[0];
            String driver = driverOrder.split(",")[0];
            String timeCoordinate = "";
            for (int i = 1; i < data.length; i++) {
                if (!data[i].equals(""))
                    timeCoordinate = data[i];
            }
            //时间，坐标 的无序列表
            String[] timeCoordinateList = timeCoordinate.split(";");

            //TimeCoord 的无序列表
            List<TimeCoord> timeCoordList = new ArrayList<>();
            for (String item : timeCoordinateList) {
                if (!item.equals("")) {
                    //时间 经度 纬度
                    String[] sjw = item.split(",");
                    if (sjw.length == 3) {
                        TimeCoord timeCoord = new TimeCoord();
                        timeCoord.time = Long.parseLong(sjw[0]);
                        timeCoord.jing = Double.parseDouble(sjw[1]);
                        timeCoord.wei = Double.parseDouble(sjw[2]);
                        timeCoordList.add(timeCoord);
                    }
                }
            }
            //排序
            Collections.sort(timeCoordList);
            //时间
            long timeTotal = timeCoordList.get(timeCoordList.size() - 1).time
                    - timeCoordList.get(0).time;
            //距离
            double distanceTotal = 0;
            //最大速度
            double speedMax = 0;
            //计算速度
            for (int i = 1; i < timeCoordList.size(); i++) {
                double thisDist = getDistance(timeCoordList.get(i), timeCoordList.get(i - 1));
                double thisSpeed = thisDist / (timeCoordList.get(i).time - timeCoordList.get(i - 1).time);
                if (thisSpeed > speedMax) {
                    speedMax = thisSpeed;
                }
                distanceTotal += thisDist;
            }
            double avgSpeed = distanceTotal / timeTotal;
            String result = String.format("%d,%f,%f,%f", timeTotal, distanceTotal, avgSpeed, speedMax);
            context.write(new Text(driver), new Text(result));
        }
    }
}

