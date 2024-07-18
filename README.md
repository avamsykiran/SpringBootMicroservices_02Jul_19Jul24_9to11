Microservice on Spring Boot
=======================================================================================

    Objectives:
    -----------------------------------------------------
    Learn and develop Spring Boot applications
    Implement Rest Web Services in the Spring Boot platform 
    Microservices Architecture and basic concepts

    Dependency Injection

        A unit of an appliction code is said to be dependent on another unit of
        the application code if the former has to call a method of the later to complete
        a task.

            A service is dependent of a DAO as the service method has to call a method
            from the DAO to complete an operation.

        Injection means passing an instance of a dependency to the place of the usage from outside.

            DAO is the dependency.
            Service is the place of usage.
            Passing an object of the DAO into the Service from outside is called DI.

            Injection by Constructor
            Injection by setter
            Injection by field

    Spring Framework offers Containers that automate the process of DI.        

    Container is a class that create and manages the objects of all the components
    of an application and as well takes care of dependency injection.

    Component is any class in an application whose objects are being managed by the container.
    The objects being managed by the container are called Beans.

    User Interfaces, Services, Repositories/DAOs / Utility classes are all Components, and the 
    objects of User Interfaces, Services, Repositories/DAOs / Utility classes are Beans.

    Spring offer two containers 
        (a) BeanFactory
        (b) ApplicationContext

    Bean Configuaraton
        Xml Based Config
        Annotation Based Config
        Java Based Config

        Annotation Based Config

            @Component
                @Service
                @Repository
                @Controller
                @ControllerAdvice
                @RestController
                @RestControllerAdvice
                ...etc.,

            @Scope
            @Value

            @Autowired
            @Qualifier
            @Primary

            @Configuration
            @ComponentScan
            @PropertySource

            @SpringBootApplication = @Configuration + @ComponentScan + @PropertySource

        Java Based Config       will allow to register the objects of thrid-party classes as beans

            @Configuration
            public class MyBeanConfig {

                @Bean
                public Scanner scan(){
                    return new Scanner(System.in);
                }

            }
    
    Spring Data JPA

        JpaRepository

    REST api / RESTful Web Services

        Resource: Employee
        EndPoint: /emps

    Operation    Http-Method     EndPoint    ReqBody     RespBody    Status-Success           Status-Failure
                                                                                            Clinet-Side     Server-Side
    -----------------------------------------------------------------------------------------------------------------                     Retrival    GET             /emps       none        Employee[]  OK-200              400-BadRequest      500-InternalServerError
                GET             /emps/101   none        Employee    OK-200              404-NotFound        500-InternalServerError

    Insert      POST            /emps       Employee    Employee    201-CREATED         400-BadRequest      500-InternalServerError
    
    Update      PUT             /emps       Employee    Employee    202-ACCEPTED        400-BadRequest      500-InternalServerError

    Delete      DELETE          /emps/101   none        none        204-NoContent       404-NotFound        500-InternalServerError

    
    Assignemnt:

        Case Study: Budget Tracking
        Create a Rest-api using spring boot with spring data jpa for persistence on an Entity - Txn.
            Txn
                txnId : Long
                amount : Double
                description : String
                txnDate : LocalDate
                txnType: TxnType

            enum TxnType
                CREDIT , DEBIT

        /statement/{year}
            List of Txns,
            startDate,
            endDate,
            totalCredit
            totalDebit
            balance

        /statement/{year}/{month}
            List of Txns,
            startDate,
            endDate,
            totalCredit
            totalDebit
            balance

MicroServices 
=========================================================================================

    Modern App Expectations

        + Interaperability
        + Granular Scalability
        + Scope to implement using enchanced method / techniques.
        + Granular Level Maintanability

    A monolythic application has all the modules in a single deployment unit. Hence
    it is definitly not possible to scale an independent module.

    Microservices is an eco-system of co-operative isolated apps that function as a
    single Application. As the apps are isolated and independent, each of the app
        1. can be developed in a different environment - interaperable
        2. granular scalability is also possible as the apps are independent.
        3. and so on all modern expectations can be met.

    Challenges while adopting to Microservices

        1. Decomposition
            converting a monolythic design into a microservice design
        2. Integration
            a. inter-service communication
            b. distributed transaction
            c. common url for a client-app to talk to the entire eco-system of microservices.
            d. auto-scaling
        3. Maintability
        4. Monitoring 
        5. Distributed Tracing

    Microservices Design Patterns

        Decomposition Design Patterns
            Decomposition by Domain
            Decomposition by Sub-domain
        Integration Design Patterns
            Api Gateway Pattern
            Aggregator Pattern
            Client Side Component Pattern
        Database Design Patterns
            Database Per Service
            Shared Database            
            Saga Pattern
            CQRS Pattern
        Observability Design Patterns
            Log Aggregator
            Performence Metrics Aggregator
            Distributed Tracing
        Cross Cutting Design Patterns
            Discovery Service 
            Circuite Breaker
            External Configuaration

    Case Study BudgetTracking APP
        1. We need to have different consumer or account holders to register
        2. Each accountHolder mst be able to record his spending or earning transactions.
        3. Generate a statement periodically displaying the total spending , the total earning and the balance.

        Decomposition By Domain

            1. profile-service          allow consuemr or accountholders to register
            2. txns-service             allow the consumer or accountholder to insert/update/delete the transactions
            3. statement-service        generate the periodic statement.

        Sub-Domain Pattern guides through bounded-context.

                We will decompose the budgetTrackinApp into 3 microservices
                    (a) Profiles-Service
                            AccountHolder
                                accountHolderId
                                fullName
                                mobileNumber
                                mailId
                                userId
                                password

                    (b) Transactions-Service
                            AccountHolder
                                accoountHolderId
                                txns: Set<Txn>

                            Txn
                                dateOfTxn
                                txnId
                                txnAmount
                                txnType
                                owner : AccountHolder

                    (c) Statement-Service                
                            AccountHolder
                                accountHolderId
                                fullName
                                mobileNumber
                                mailId

                            Txn
                                dateOfTxn
                                txnId
                                txnAmount
                                txnType

                            Statement
                                owner : AccountHolder
                                txns: Set<txns>
                                startDate: Date
                                endDate: Date
                                totalSpending
                                totalEarning
                                balance

            Shared Database Pattern

                Having a single DB for all microservices
                in brown field apps

            Database Per Service Pattern

                Each microservice has its own database
                in all green field apps

            Discovery Service Pattern

                discovery-service
                    |
                    |- all microservices will register their address with discovery-service
                    |- the address are retrived from here by the needy microservices

            Data Aggregation Pattern

                Aggregation is about desiging a microservice that can collect info
                from other microservices analyze and aggreagate the data and pass the 
                aggregated data to the client, sacing the client from making multiple requests
                for different parts of the data.

                the 'statement-microservice' is an example for this pattern.

            Client Side Component Pattern

                Each component of the UI/UX application can place
                their individual reqeusts to different microservices parellelly
                and should be receiving the resposnes as well parllelly.

            Distributed Tracing Design Pattern

                Tracing - Service

                    Whenever a request comes to any of the microservices in our app-ecosystem,
                    that request is given a unique ID and is reported to the Tracing-Service
                    every time, the request goes from one service to another service until
                    the final resposne is sent to the clinet. And the tracing-service 
                    will record all the track of tehis request along with any performence
                    metrics and log info attached with the request.

            Load Balancing Design Pattern

                load balalcing means mapping the incoming reqeusts to multiple instacnes of the 
                same microservice based on some (round-robin) algorithm.

                tools like Ribbon / Spring Cloud Load Balancer ..etc., are used to perform load 
                balancing.

            API Gateway Design Pattern

                gateway-service <------------(all reqs)--------------- any-client
                    |
                    | -> forward that request to the respective micro-service
                    | <- receives the response from that micro-service
                    |
                    |----------------------------------(response)---------> client

            Circuit - Breaker Design Pattern

                circuit-breaker-thrushold

                    when the first request could not reach a specific microservice (due to its down-time),
                    a fallback machanisim is triggered.

                    After that the circuit is made open (broken), means that the fallback machanisim
                    will address all the other consiquitive request targetting that microservice.

                    When a request to the sme micro-service is inbound after the thrushold, then the
                    circuit is half-closed, means that a new attemp to reach the microserivce is made,
                            }|- on successful contact, the circuit is closed
                             |- or if that microservice is still unavailable, the circuit continues to be open.

                    tools like Resiliance4j ..etc., are for the purpose.

            External-Cofiguaration Design Pattern

                    repository (github) [contains a list all config files of all microservice]
                        |
                        |                
                config-service
                            |<- when ever a microservice has to start, it will first send a fetech req the
                            |   the config-service
                            |
                            | the config-service will check for the cofnig file in the repo
                            |
                            |<- the config file is passed to the microservcei by the config-service
                            |
                            |<- wheever the config fiels are modified and pushed into the repo
                            |   the config service will automatically notify all the respective microservices
                                    and the microservices will receive the updated config-file and restart all by 
                                    themselves.

Decomposition by domain and sub-domain

    budgettracking 
        profiles service
            AccountHolder Entity
                Long ahId
                String fullName
                String mobile
                String mailId

        txns service
            AccountHolder Entity
                Long ahId
                Double currentBalance
                Set<Txn> txns
            Txn           Entity
                Long txnId
                String header
                Double amount
                TxnType type
                LocalDate txnDate
                AccountHolder holder

        statement service
            AccountHolder Model
                Long ahId
                String fullName
                String mobile
                String mailId
                Double currentBalance

            Txn           Model
                Long txnId
                String header
                Double amount
                TxnType type
                LocalDate txnDate

            Statement     Model
                LocalDate start
                LocalDate end
                AccountHolder profile
                Set<Txn> txns
                totalCredit
                totalDebit
                statementBalance

Aggregator Pattern

    req for statement ------------> statement-service ---------------> profile service
                                                    <---account holder data---
                                                    --------------------> txns service
                                                    <----list of txns-------
                                     does the composition and computation
            <---statement obj-------  into statement obj

Discovery Service Design Pattern

                discovery-service
                (spring cloud netflix eureka discovery service)
                        ↑|
                    registration of urls 
                    and retrival of urls
                        |↓
            -------------------------------------
            |               |                   |
    profile-service     txns-service     statement-service

Api Gateway Design Pattern

    Andriod App/Angular App/ReactJS App
        ↑↓
     api-gateway
     (spring cloud api gateway)
        |
        |
        | <---->   discovery-service
            ↑    ( netflix eureka discovery service)
            |            ↑|
            |        registration of urls 
            |       and retrival of urls
            ↓            |↓
            -------------------------------------
            |               |                   |                
    profile-service     txns-service     statement-service
       

Distributed Tracing

  Andriod App/Angular App/ReactJS App
        ↑↓
     api-gateway
     (spring cloud api gateway)
        |
        |
        | <---->   discovery-service
            ↑    ( netflix eureka discovery service)
            |            ↑|
            |        registration of urls 
            |       and retrival of urls
            ↓            |↓
            -------------------------------------
            |               |                   |                
    profile-service     txns-service     statement-service
        (sleuth)          (sleuth)            (sleuth)
            |               |                      |
            -------------------------------------------
                        ↑↓
             distrubuted tracing service
                    (zipkin-server)

External Configuaration

  Andriod App/Angular App/ReactJS App
        ↑↓
     api-gateway
     (spring cloud api gateway)
        |
        |
        | <---->   discovery-service
            ↑    ( netflix eureka discovery service)
            |            ↑|
            |        registration of urls 
            |       and retrival of urls
            ↓            |↓
            -------------------------------------
            |               |                   |                
    profile-service     txns-service     statement-service
        (sleuth)          (sleuth)            (sleuth)
            |               |                      |
            -------------------------------------------
                        ↑↓                      ↑↓
             distrubuted tracing service       configuaration-service 
                    (zipkin-server)         (spring cloud config service)
                                                    |
                                                    |
                                                    git-repo
                                                        profile.properties
                                                        txns.properties
                                                        statement.properties
                                                        gateway.properties

Implementing Budget-tracker
                                        
    Step#1  implementing decomposed services and do inter-service communication and aggregator
        in.bta:bta-profiles
            dependencies
                org.springframework.boot:spring-boot-starter-web
                org.springframework.boot:spring-boot-devtools
                org.springframework.cloud:spring-cloud-openfeign
                mysq1:mysql-connector-java
                org.springframework.boot:spring-boot-starter-data-jpa
            configuaration
                spring.application.name=profiles
                server.port=9100

                spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
                spring.datasource.username=root
                spring.datasource.password=root
                spring.datasource.url=jdbc:mysql://localhost:3306/bapsDB?createDatabaseIfNotExist=true
                spring.jpa.hibernate.ddl-auto=update

        in.bta:bta-txns
            dependencies
                org.springframework.boot:spring-boot-starter-web
                org.springframework.boot:spring-boot-devtools
                org.springframework.cloud:spring-cloud-openfeign
                mysq1:mysql-connector-java
                org.springframework.boot:spring-boot-starter-data-jpa
            configuaration
                spring.application.name=txns
                server.port=9200

                spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
                spring.datasource.username=root
                spring.datasource.password=root
                spring.datasource.url=jdbc:mysql://localhost:3306/batxnsDB?createDatabaseIfNotExist=true
                spring.jpa.hibernate.ddl-auto=update

        in.bta:bta-statement
            dependencies
                org.springframework.boot:spring-boot-starter-web
                org.springframework.boot:spring-boot-devtools
                org.springframework.cloud:spring-cloud-openfeign
            configuaration
                spring.application.name=statement
                server.port=9300

    Step#2  implementing discovery service and client side load balancing
        in.bta:bta-discovery
            dependencies
                org.springframework.boot:spring-boot-devtools
                org.springframework.cloud:spring-cloud-starter-netflix-eureka-server
            configuaration
                @EnableEurekaServer    on Application class

                spring.application.name=discovery
                server.port=9000

                eureka.instance.hostname=localhost
                eureka.client.registerWithEureka=false
                eureka.client.fetchRegistry=false
                eureka.client.serviceUrl.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/
                eureka.server.waitTimeInMsWhenSyncEmpty=0

        in.bta:bta-profiles
            dependencies
                ++ org.springframework.cloud:spring-cloud-starter-netflix-eureka-client
                ++ org.springframework.cloud:spring-cloud-starter-loadbalancer
            configuaration
                ++@EnableDiscoveryClient  on Application class

                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/
                eureka.client.initialInstanceInfoReplicationIntervalSeconds=5
                eureka.client.registryFetchIntervalSeconds=5
                eureka.instance.leaseRenewalIntervalInSeconds=5
                eureka.instance.leaseExpirationDurationInSeconds=5

                spring.cloud.loadbalancer.ribbon.enabled=false

        in.bta:bta-txns
            dependencies
                ++ org.springframework.cloud:spring-cloud-starter-netflix-eureka-client
                ++ org.springframework.cloud:spring-cloud-starter-loadbalancer
            configuaration
                ++@EnableDiscoveryClient  on Application class

                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/
                eureka.client.initialInstanceInfoReplicationIntervalSeconds=5
                eureka.client.registryFetchIntervalSeconds=5
                eureka.instance.leaseRenewalIntervalInSeconds=5
                eureka.instance.leaseExpirationDurationInSeconds=5

                spring.cloud.loadbalancer.ribbon.enabled=false

        in.bta:bta-statement
            dependencies
                ++ org.springframework.cloud:spring-cloud-starter-netflix-eureka-client
                ++ org.springframework.cloud:spring-cloud-starter-loadbalancer
            configuaration
                ++@EnableDiscoveryClient  on Application class

                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/
                eureka.client.initialInstanceInfoReplicationIntervalSeconds=5
                eureka.client.registryFetchIntervalSeconds=5
                eureka.instance.leaseRenewalIntervalInSeconds=5
                eureka.instance.leaseExpirationDurationInSeconds=5

                spring.cloud.loadbalancer.ribbon.enabled=false    

    Step 3: Implement API Gateway Design Pattern
        in.bta:bta-gateway
            dependencies
                org.springframework.boot:spring-boot-devtools
                org.springframework.cloud:spring-cloud-starter-gateway
                org.springframework.cloud:spring-cloud-starter-netflix-eureka-client
                org.springframework.cloud:spring-cloud-starter-loadbalancer
            configuaration
                @EnableDiscoveryClient          on Application class

                spring.application.name=gateway
                server.port=9999

                eureka.client.serviceUrl.defaultZone=http://localhost:9000/eureka/
                eureka.client.initialInstanceInfoReplicationIntervalSeconds=5
                eureka.client.registryFetchIntervalSeconds=5
                eureka.instance.leaseRenewalIntervalInSeconds=5
                eureka.instance.leaseExpirationDurationInSeconds=5

                spring.cloud.gateway.discovery.locator.enabled=true
                spring.cloud.gateway.discovery.locator.lower-case-service-id=true
                
        in.bta:bta-discovery
        in.bta:bta-profiles
        in.bta:bta-txns
        in.bta:bta-statement
              
   