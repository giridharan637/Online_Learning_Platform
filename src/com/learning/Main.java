
import com.learning.models.Student;
import com.learning.models.Instructor;
import com.learning.models.Admin;

import com.learning.repository.UserRepository;
import com.learning.repository.CourseRepository;
import com.learning.repository.EnrollmentRepository;
import com.learning.repository.ProgressRepository;
import com.learning.repository.PaymentRepository;
import com.learning.repository.ReviewRepository;
import com.learning.repository.CertificateRepository;

import com.learning.service.UserService;
import com.learning.service.CourseService;
import com.learning.service.EnrollmentService;
import com.learning.service.ProgressService;
import com.learning.service.PaymentService;
import com.learning.service.ReviewService;
import com.learning.service.CertificateService;

import com.learning.cli.MainCLI;
import com.learning.cli.CourseCLI;
import com.learning.cli.EnrollmentCLI;
import com.learning.cli.ProgressCLI;
import com.learning.cli.PaymentCLI;
import com.learning.cli.ReviewCLI;
import com.learning.cli.CertificateCLI;

public class Main {

    public static void main(String[] args) {

        System.out.println("Welcome to Online Learning Platform!");

        // 1. Create sample users
        Student student = new Student(
                101,
                "Arun",
                "arun@gmail.com"
        );

        Instructor instructor = new Instructor(
                201,
                "Priya",
                "priya@gmail.com"
        );

        Admin admin = new Admin(
                301,
                "Admin",
                "admin@gmail.com"
        );

        // 2. Create repositories
        UserRepository userRepository = new UserRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentRepository enrollmentRepository =
                new EnrollmentRepository();
        ProgressRepository progressRepository =
                new ProgressRepository();
        PaymentRepository paymentRepository =
                new PaymentRepository();
        ReviewRepository reviewRepository =
                new ReviewRepository();
        CertificateRepository certificateRepository =
                new CertificateRepository();

        // 3. Create services
        UserService userService =
                new UserService(userRepository);

        CourseService courseService =
                new CourseService(courseRepository);

        EnrollmentService enrollmentService =
                new EnrollmentService(enrollmentRepository);

        ProgressService progressService =
                new ProgressService(progressRepository);

        PaymentService paymentService =
                new PaymentService(
                        paymentRepository,
                        enrollmentRepository,
                        progressRepository
                );

        ReviewService reviewService =
                new ReviewService(
                        reviewRepository,
                        enrollmentRepository,
                        progressRepository
                );

        CertificateService certificateService =
                new CertificateService(certificateRepository);

        // 4. Register sample users
        userService.createUser(student);
        userService.createUser(instructor);
        userService.createUser(admin);

        // 5. Create CLI modules
        CourseCLI courseCLI =
                new CourseCLI(courseService, instructor);

        EnrollmentCLI enrollmentCLI =
                new EnrollmentCLI(
                        enrollmentService,
                        userService,
                        courseService
                );

        ProgressCLI progressCLI =
                new ProgressCLI(
                        progressService,
                        enrollmentService
                );

        PaymentCLI paymentCLI =
                new PaymentCLI(
                        paymentService,
                        enrollmentService
                );

        ReviewCLI reviewCLI =
                new ReviewCLI(
                        reviewService,
                        enrollmentService,
                        userService
                );

        CertificateCLI certificateCLI =
                new CertificateCLI(
                        certificateService,
                        enrollmentService
                );

        // 6. Start the main menu
        MainCLI mainCLI = new MainCLI(courseCLI, enrollmentCLI, progressCLI, paymentCLI, reviewCLI, certificateCLI);
        mainCLI.showMenu();
    }
}
