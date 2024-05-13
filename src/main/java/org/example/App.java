package org.example;


import akka.http.javadsl.server.HttpApp;
import akka.http.javadsl.server.Route;
import org.example.repository.DataRepo;
import org.example.repository.DbClient;
import org.example.route.StatusRoute;
import org.example.route.UserRoute;
import org.example.service.KafkaConsumerService;
import org.example.service.UserServiceImpl;
import org.slf4j.Logger;


import java.util.UUID;

import static org.example.helper.ConfigHelper.port;
import static org.example.helper.ConfigHelper.profile;
import static org.slf4j.LoggerFactory.getLogger;

// json ++
// rest ++
// crud operation ++
// config ++
// log
// hikari
// kafka
// DbClient ++
// DataRepo ++
// application.conf ++
// route ++

public class App extends HttpApp {

    private static final Logger log = getLogger(App.class);

    private final Route route;



    public App() {

        DbClient dbClient = new DbClient();

        String bootstrapServers = "localhost:9092";
        String groupId = "best";
        String topic = "dutTopic";

        DataRepo dataRepo = new DataRepo(dbClient);

        KafkaConsumerService consumerService = new KafkaConsumerService(bootstrapServers, groupId, topic, dataRepo);
        consumerService.consumeMessages();

        UserServiceImpl userService = new UserServiceImpl(dataRepo);
        route = concat(
                new UserRoute(userService).route,
                new StatusRoute().route
        );
    }




    public static void main(String[] args) throws Exception {
//        UUID cid = UUID.randomUUID();
//        log.info("[56da880d-6507-46a1-ab7d-595d5e6fe35c] up...");
//        log.debug("[56da880d-6507-46a1-ab7d-595d5e6fe35c]up...");
//        log.error("[56da880d-6507-46a1-ab7d-595d5e6fe35c]up...");
//        log.info("active profile: {}, http port: {}", profile, port);
        new App().startServer("0.0.0.0", port);
    }

    @Override
    public Route routes() {
        return route;
    }
}


