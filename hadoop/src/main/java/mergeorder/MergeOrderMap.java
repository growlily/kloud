package mergeorder;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

//输出 Key：司机，订单，Value：时间，坐标
public class MergeOrderMap extends Mapper<Object, Text, Text, Text> {

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] data = value.toString().split(",");
        if (data.length == 5) {
            String driverOrder = data[0] + "," + data[1];
            String timeCoordinate = data[2] + "," + data[3] + "," + data[4];
            context.write(new Text(driverOrder), new Text(timeCoordinate));
        }
    }
}
