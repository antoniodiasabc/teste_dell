package br.com.atech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import br.com.atech.controler.HangManControler;


@SpringBootApplication
@ComponentScan(basePackages = { "br.com.atech.*" })
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class ApplicationHangMan {

	@Autowired
	HangManControler hangmanControler;

	// private static final SimpleDateFormat sdf = new
	// SimpleDateFormat("yyyy-MM-dd");

	//@Autowired
	//DataSource dataSource;

	//@Autowired
	//ExecutorEstatistico executor;

	// @Autowired
	// O DynamicReportsRepository dynRepo;

	// @Autowired
	// CustomerRepository customerRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApplicationHangMan.class, args);
	}
//
//	@Bean("threadPoolTaskExecutor")
//	public TaskExecutor getAsyncExecutor() {
//		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//		executor.setCorePoolSize(200);
//		executor.setMaxPoolSize(10000);
//		executor.setWaitForTasksToCompleteOnShutdown(true);
//		executor.setThreadNamePrefix("RelEstatV1-");
//		return executor;
//	}

//	@Bean
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//		return args -> {
//
//			System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//			executor.asyncMethodWithConfiguredExecutor();
//
//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			for (String beanName : beanNames) {
//				System.out.println(beanName);
//			}
//
//		};
//	}

//	@Transactional(readOnly = true)
//	@Override
//	public void run(String... args) throws Exception {

		//System.out.println("DATASOURCE = " + dataSource);

		// executor.asyncMethodWithConfiguredExecutor();

		// System.out.println("\n1.findAll()...");
		// for (Customer customer : customerRepository.findAll()) {
		// System.out.println(customer);
		// }
		//
		// System.out.println("\n2.findByEmail(String email)...");
		// for (Customer customer : customerRepository.findByEmail("222@yahoo.com")) {
		// System.out.println(customer);
		// }
		//
		// System.out.println("\n3.findByDate(Date date)...");
		// for (Customer customer :
		// customerRepository.findByDate(sdf.parse("2017-02-12"))) {
		// System.out.println(customer);
		// }
		//
		// // For Stream, need @Transactional
		// System.out.println("\n4.findByEmailReturnStream(@Param(\"email\") String
		// email)...");
		// try (Stream<Customer> stream =
		// customerRepository.findByEmailReturnStream("333@yahoo.com")) {
		// stream.forEach(x -> System.out.println(x));
		// }
		//
		// //System.out.println("....................");
		// //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// //Date from = sdf.parse("2017-02-15");
		// //Date to = sdf.parse("2017-02-17");
		//
		// //for (Customer customer : customerRepository.findByDateBetween(from, to)) {
		// // System.out.println(customer);
		// //}

		//System.out.println("Done!");

		// exit(0);
	//}

}