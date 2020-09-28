package mergeorder;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

//输出 Key：司机，Value：时间坐标的无序列表
public class MergeOrderReduce extends Reducer<Text, Text, Text, Text> {
    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        String list = "";
        for (Text val : values) {
            list = list.concat(val.toString()).concat(";");
        }
        Text result = new Text(list);
        context.write(key, result);
    }
}
