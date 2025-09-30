//import model.Benefit;
//import model.Employee;
//import service.BenefitManager;
//import service.EmployeeManager;
//
//public class MainTest {
//    public static void main(String[] args) {
//        // === Test pentru EmployeeManager ===
//        EmployeeManager empManager = new EmployeeManager();
//
//        // Adăugăm angajați
//        Employee e1 = new Employee(1, "Ana");
//        Employee e2 = new Employee(2, "Mihai");
//        empManager.addEmployee(e1);
//        empManager.addEmployee(e2);
//
//        System.out.println("Lista angajaților:");
//        for (Employee e : empManager.getAllEmployees()) {
//            System.out.println(e);
//        }
//
//        // Căutare după nume
//        System.out.println("\nCăutăm pe Mihai:");
//        System.out.println(empManager.findEmployeeByName("Mihai"));
//
//        // Update (înlocuim Mihai cu Mihai Popescu)
//        Employee e2Updated = new Employee(2, "Mihai Popescu");
//        empManager.updateEmployee("Mihai", e2Updated);
//
//        System.out.println("\nDupă update:");
//        for (Employee e : empManager.getAllEmployees()) {
//            System.out.println(e);
//        }
//
//        // Ștergere
//        empManager.deleteEmployee("Ana");
//        System.out.println("\nDupă ștergere (Ana eliminată):");
//        for (Employee e : empManager.getAllEmployees()) {
//            System.out.println(e);
//        }
//
//        // === Test pentru BenefitManager ===
//        BenefitManager benManager = new BenefitManager();
//
//        // Adăugăm beneficii
//        Benefit b1 = new Benefit(101, "Abonament sală", 150);
//        Benefit b2 = new Benefit(102, "Asigurare medicală", 200);
//        benManager.addBenefit(b1);
//        benManager.addBenefit(b2);
//
//        System.out.println("\nLista beneficiilor:");
//        for (Benefit b : benManager.getAllBenefits()) {
//            System.out.println(b);
//        }
//
//        // Căutare după nume
//        System.out.println("\nCăutăm 'Asigurare medicală':");
//        System.out.println(benManager.findBenefitByName("Asigurare medicală"));
//
//        // Update (schimbăm costul asigurării medicale)
//        Benefit b2Updated = new Benefit(102, "Asigurare medicală premium", 350);
//        benManager.updateBenefit("Asigurare medicală", b2Updated);
//
//        System.out.println("\nDupă update:");
//        for (Benefit b : benManager.getAllBenefits()) {
//            System.out.println(b);
//        }
//
//        // Ștergere
//        benManager.deleteBenefit("Abonament sală");
//        System.out.println("\nDupă ștergere (Abonament sală eliminat):");
//        for (Benefit b : benManager.getAllBenefits()) {
//            System.out.println(b);
//        }
//    }
//}
