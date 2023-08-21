package br.com.solutis.locadora.seed;


import br.com.solutis.locadora.model.entity.car.*;
import br.com.solutis.locadora.model.entity.cart.Cart;
import br.com.solutis.locadora.model.entity.person.Driver;
import br.com.solutis.locadora.model.entity.person.Employee;
import br.com.solutis.locadora.model.entity.person.GenderEnum;
import br.com.solutis.locadora.model.entity.rent.InsurancePolicy;
import br.com.solutis.locadora.model.entity.rent.Rent;
import br.com.solutis.locadora.repository.car.AccessoryRepository;
import br.com.solutis.locadora.repository.car.CarRepository;
import br.com.solutis.locadora.repository.car.ManufacturerRepository;
import br.com.solutis.locadora.repository.car.ModelRepository;
import br.com.solutis.locadora.repository.cart.CartRepository;
import br.com.solutis.locadora.repository.person.DriverRepository;
import br.com.solutis.locadora.repository.person.EmployeeRepository;
import br.com.solutis.locadora.repository.rent.InsurancePolicyRepository;
import br.com.solutis.locadora.repository.rent.RentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Component
public class SeedData implements CommandLineRunner {
    private final ManufacturerRepository manufacturerRepository;
    private final ModelRepository modelRepository;
    private final CarRepository carRepository;
    private final AccessoryRepository accessoryRepository;
    private final DriverRepository driverRepository;
    private final EmployeeRepository employeeRepository;
    private final InsurancePolicyRepository insurancePolicyRepository;
    private final RentRepository rentRepository;
    private final CartRepository cartRepository;

    static String getAlphaNumericString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public String generateCPF() {
        int digit1, digit2, remainder;
        String concatenatedDigits, generatedNumber;

        Random randomGenerator = new Random();

        int[] digits = new int[9];
        for (int i = 0; i < 9; i++) {
            digits[i] = randomGenerator.nextInt(10);
        }

        int sum = digits[8] * 2 + digits[7] * 3 + digits[6] * 4 + digits[5] * 5 + digits[4] * 6 +
                digits[3] * 7 + digits[2] * 8 + digits[1] * 9 + digits[0] * 10;

        int value = (sum / 11) * 11;
        digit1 = sum - value;
        remainder = (digit1 % 11);

        if (digit1 < 2) {
            digit1 = 0;
        } else {
            digit1 = 11 - remainder;
        }

        int sum2 = digit1 * 2 + digits[8] * 3 + digits[7] * 4 + digits[6] * 5 + digits[5] * 6 +
                digits[4] * 7 + digits[3] * 8 + digits[2] * 9 + digits[1] * 10 + digits[0] * 11;

        int value2 = (sum2 / 11) * 11;
        digit2 = sum2 - value2;
        remainder = (digit2 % 11);

        if (digit2 < 2) {
            digit2 = 0;
        } else {
            digit2 = 11 - remainder;
        }

        concatenatedDigits = digits[0] + String.valueOf(digits[1]) + digits[2] + "." + digits[3] +
                digits[4] + digits[5] + "." + digits[6] + digits[7] +
                digits[8] + "-";

        generatedNumber = concatenatedDigits + digit1 + digit2;
        return generatedNumber;
    }


    @Override
    public void run(String... args) throws Exception {
        List<String> manufacturerNames = Arrays.asList("Fiat", "Ford", "Hyundai", "Toyota", "BMW");

        for (int i = 0; i < manufacturerNames.toArray().length; i++) {
            Manufacturer manufacturer = new Manufacturer();
            Accessory accessory = new Accessory();
            Car car = new Car();
            Driver driver = new Driver();
            Employee employee = new Employee();
            InsurancePolicy insurancePolicy = new InsurancePolicy();
            Rent rent = new Rent();
            Model model = new Model();
            Cart cart = new Cart();

            manufacturer.setName(manufacturerNames.get(i));
            manufacturerRepository.save(manufacturer);

            for (ModelCategoryEnum models : ModelCategoryEnum.values()) {
                model.setCategory(models);
                model.setDescription(getAlphaNumericString(5));
                model.setManufacturer(manufacturer);
                modelRepository.save(model);
            }

            List<Accessory> accessories = new ArrayList<>();
            accessory.setDescription("Car accessory example " + i);
            accessoryRepository.save(accessory);
            accessories.add(accessory);

            car.setPlate(getAlphaNumericString(8));
            car.setChassis(getAlphaNumericString(6));
            car.setColor("Red");
            car.setDailyValue(new BigDecimal("100.00"));
            car.setImageUrl("https://example.com/car-image.jpg");
            car.setModel(model);
            car.setAccessories(accessories);
            carRepository.save(car);

            driver.setCnh(getAlphaNumericString(7));
            driver.setName("Name " + i);
            driver.setCpf(generateCPF());
            driver.setBirthDate(LocalDate.now().minusYears(20));

            for (GenderEnum genders : GenderEnum.values()) {
                driver.setGender(genders);
            }
            driverRepository.save(driver);

            employee.setName("Name: " + i);
            employee.setCpf(generateCPF());
            employee.setBirthDate(LocalDate.now().minusYears(20));

            for (GenderEnum genders : GenderEnum.values()) {
                employee.setGender(genders);
            }
            employee.setRegistration(getAlphaNumericString(11));
            employeeRepository.save(employee);

            insurancePolicy.setFranchiseValue(new BigDecimal(i * 10));
            cart.setDriver(driver);
            cartRepository.save(cart);

            List<Rent> rents = new ArrayList<>();
            rent.setStartDate(LocalDate.now());
            rent.setFinished(false);
            rent.setConfirmed(false);
            rent.setEndDate(LocalDate.now().plusWeeks(2));
            rent.setValue(new BigDecimal(15 * i));
            rent.setInsurancePolicy(insurancePolicy);
            rent.setDriver(driver);
            rent.setCar(car);
            rent.setCart(cart);
            rents.add(rent);

            insurancePolicy.setCarRents(rents);
            insurancePolicyRepository.save(insurancePolicy);
            cart.setRents(rents);
            rentRepository.save(rent);
        }
    }
}