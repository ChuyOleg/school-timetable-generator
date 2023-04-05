package ip91.chui.oleh;

//import ip91.chui.oleh.model.GroupLimitsFactory;
//import ip91.chui.oleh.model.SimpleFitnessFunction;
//import ip91.chui.oleh.model.SimpleScheduleGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class 	BackendApplication {

	public static void main(String[] args) {
//		GroupLimitsFactory groupLimitsFactory = new GroupLimitsFactory();
//		SimpleScheduleGenerator generator = new SimpleScheduleGenerator(new Random(), groupLimitsFactory);
//		SimpleFitnessFunction fitnessFunction = new SimpleFitnessFunction();
//		new Runner(groupLimitsFactory, generator, fitnessFunction).run();
		SpringApplication.run(BackendApplication.class, args);
	}

}
