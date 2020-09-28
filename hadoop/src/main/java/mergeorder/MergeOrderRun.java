package mergeorder;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class MergeOrderRun {
    public static void run() throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        // 1.实例化一个Job
        Job job = Job.getInstance(conf, "mergeOrder");
        // 2.设置mapper类
        job.setMapperClass(MergeOrderMap.class);
        // 3.设置Combiner类 不是必须的
        job.setCombinerClass(MergeOrderReduce.class);
        // 4.设置Reducer类
        job.setReducerClass(MergeOrderReduce.class);
        // 5.设置输出key的数据类型
        job.setOutputKeyClass(Text.class);
        // 6.设置输出value的数据类型
        job.setOutputValueClass(Text.class);
        // 设置通过哪个类查找job的Jar包
        job.setJarByClass(MergeOrderRun.class);
        // 7.设置输入路径,这个得改一下
        FileInputFormat.addInputPath(job, new Path("/didi/data/gps_20161001"));
        // 8.设置输出路径
        FileOutputFormat.setOutputPath(job, new Path("/didi/data/gps_20161001_mergeOrder"));
        // 9.执行该作业
        System.out.println(job.waitForCompletion(true));
    }
}
