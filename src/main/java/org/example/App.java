package org.example;


import akka.http.javadsl.server.HttpApp;
import akka.http.javadsl.server.Route;
import org.example.repository.DataRepo;
import org.example.repository.DbClient;
import org.example.route.StatusRoute;
import org.example.route.UserRoute;
import org.example.service.UserServiceImpl;
import org.slf4j.Logger;


import static org.example.helper.ConfigHelper.port;
import static org.example.helper.ConfigHelper.profile;
import static org.slf4j.LoggerFactory.getLogger;

// json ++
// rest ++
// crud operation ++
// config ++
// log
// hikari ++
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

        DataRepo dataRepo = new DataRepo(dbClient);

        UserServiceImpl userService = new UserServiceImpl(dataRepo);

        route = concat(
                new UserRoute(userService).route,
                new StatusRoute().route
        );
    }




    public static void main(String[] args) throws Exception {
        log.info("up...");
        log.info("active profile: {}, http port: {}", profile, port);
        new App().startServer("0.0.0.0", port);
    }

    @Override
    public Route routes() {
        return route;
    }
}


