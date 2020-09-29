package orderspeed;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class OrderSpeedRun {
    public static void run() throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        // 1.实例化一个Job
        Job job = Job.getInstance(conf, "driverSpeed");
        // 2.设置mapper类
        job.setMapperClass(OrderSpeedMap.class);
        // 3.设置Combiner类 不是必须的
        job.setCombinerClass(OrderSpeedReduce.class);
        // 4.设置Reducer类
        job.setReducerClass(OrderSpeedReduce.class);
        // 5.设置输出key的数据类型
        job.setOutputKeyClass(Text.class);
        // 6.设置输出value的数据类型
        job.setOutputValueClass(Text.class);
        // 设置通过哪个类查找job的Jar包
        job.setJarByClass(OrderSpeedRun.class);
        // 7.设置输入路径,这个得改一下
        FileInputFormat.addInputPath(job, new Path("/didi/data/gps_20161001_mergeOrder/part-r-00000"));
        // 8.设置输出路径
        FileOutputFormat.setOutputPath(job, new Path("/didi/data/gps_20161001_driverSpeed"));
        // 9.执行该作业
        System.out.println(job.waitForCompletion(true));
    }
}
