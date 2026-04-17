package kantseryk.pzks;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Alona Kantseryk
 */

class CompanyServiceImplTest {

    private final Company main = new Company(null,2);
    private final Company book = new Company(main,3);
    private final Company manager = new Company(main,4);
    private final Company developer = new Company(manager,8);
    private final Company design = new Company(manager,6);
    private final Company lawer = new Company(null,1);

    private final List<Company> list = List.of(main, book, manager,developer, design);

    private final ICompanyService companyService = new CompanyServiceImpl();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenCompanyIsNullThenNull() {
        Company result = companyService.getTopLevelParent(null);
        Assertions.assertNull( result);
    }

    @Test
    void whenCompanyHasNoParentItIsOnTop() {
        Company result = companyService.getTopLevelParent(main);
        Assertions.assertEquals(main, result);
    }

    @Test
    void whenCompanyIsSingleItIsOnTop() {
        Company result = companyService.getTopLevelParent(lawer);
        Assertions.assertEquals(lawer, result);
    }
    @Test
    void whenCompanyHasOneStepToTheTopThenFindTop() {
        Company result = companyService.getTopLevelParent(book);
        Assertions.assertEquals(main, result);
    }
    @Test
    void whenCompanyHasTwoStepsToTheTopThenFindTop() {
        Company result = companyService.getTopLevelParent(developer);
        Assertions.assertEquals(main, result);
    }

    @Test
    void whenCompanyIsNullForEmployeeCountThenZero() {
        long result = companyService.getEmployeeCountForCompanyAndChildren(null, list);
        assertEquals(0, result);
    }

    @Test
    void whenCompanyHasNoChildrenThenReturnOnlyOwnEmployees() {
        long result = companyService.getEmployeeCountForCompanyAndChildren(book, list);
        assertEquals(3, result);
    }

    @Test
    void whenCompanyHasChildrenThenReturnOwnAndChildrenEmployees() {
        long result = companyService.getEmployeeCountForCompanyAndChildren(manager, list);
        assertEquals(18, result);
    }

    @Test
    void whenCompanyHasAllLevelsThenReturnEmployeesForWholeTree() {
        long result = companyService.getEmployeeCountForCompanyAndChildren(main, list);
        assertEquals(23, result);
    }

    @Test
    void whenSingleCompanyIsNotInListThenReturnOnlyOwnEmployees() {
        long result = companyService.getEmployeeCountForCompanyAndChildren(lawer, list);
        assertEquals(1, result);
    }


    @Test
    void whenCompaniesListIsNullThenReturnOnlyOwnEmployees() {
        long result = companyService.getEmployeeCountForCompanyAndChildren(manager, null);
        assertEquals(4, result);
    }

    @Test
    void whenCompaniesListIsEmptyThenReturnOnlyOwnEmployees() {
        long result = companyService.getEmployeeCountForCompanyAndChildren(manager, List.of());
        assertEquals(4, result);
    }

    @Test
    void whenCompaniesListContainsNullThenIgnoreNullAndCountCorrectly() {
        List<Company> companiesWithNull = Arrays.asList(main, book, manager, developer, design, null);
        long result = companyService.getEmployeeCountForCompanyAndChildren(main, companiesWithNull);
        assertEquals(23, result);
    }

    @Test
    void whenAnotherChildHasOneStepToTheTopThenFindTop() {
        Company result = companyService.getTopLevelParent(manager);
        Assertions.assertEquals(main, result);
    }
}