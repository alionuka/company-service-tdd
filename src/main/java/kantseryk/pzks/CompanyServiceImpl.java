package kantseryk.pzks;

import java.util.List;

/**
 * Author: Alona Kantseryk
 */
public class CompanyServiceImpl implements ICompanyService {
    @Override
    public Company getTopLevelParent(Company child) {
        if (child == null) {
            return null;
        }
        if (child.getParent() == null) {
            return child;
        }

        Company current = child;
        while (current.getParent() != null) {
            current = current.getParent();
        }
        return current;
    }

    @Override
    public long getEmployeeCountForCompanyAndChildren(Company company, List<Company> companies) {
        if (company == null) {
            return 0;
        }

        long result = company.getEmployeesCount();

        if (companies == null || companies.isEmpty()) {
            return result;
        }

        for (Company current : companies) {
            if (current != null && current.getParent() == company) {
                result += getEmployeeCountForCompanyAndChildren(current, companies);
            }
        }

        return result;
    }
}
