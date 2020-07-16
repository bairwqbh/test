package per.cby.test.mongo;
//package per.cby.test.mongo;
//
//import org.junit.Test;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//
//import per.cby.frame.common.base.BaseBootTest;
//import per.cby.frame.task.model.Task;
//import per.cby.frame.task.mongo.TaskMongo;
//
//public class MongoTest extends BaseBootTest {
//
//    /**
//     * 任务调度信息Mongo存储
//     * 
//     * @return 任务调度信息Mongo存储
//     */
//    @Bean
//    public TaskMongo taskMongo() {
//        return new TaskMongo();
//    }
//
//    @Test
//    public void test() {
//        String name = "abc123";
//        Query query = Query.query(Criteria.where("name").is(name));
//        Task task = taskMongo().findOne(query);
//        if (task == null) {
//            task = new Task();
//            task.setName(name);
//        }
//        taskMongo().save(task);
//        Task task1 = taskMongo().findOne(query);
//        taskMongo().save(task1);
//        System.out.println();
//    }
//
//}
