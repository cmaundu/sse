package co.ke.sart.site.repository;

import co.ke.sart.site.entity.PaymentSummary;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository("paymentSummaryRepository")
public class PaymentSummaryRepository {

    SimpleJdbcCall procCall;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    public List<PaymentSummary> getDailySummary() {
        List<PaymentSummary> list = new ArrayList<>();

        procCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("sart_proc_daily_summaries")
                .returningResultSet("result", (rs, i) -> {
                    return mapResultRow(rs);
                });

        Map m = procCall.execute(new HashMap<String, Object>(0));
        return (List) m.get("result");        

    }

    private PaymentSummary mapResultRow(ResultSet rs) throws SQLException {
        PaymentSummary ps = new PaymentSummary();
        ps.setName(rs.getString("pt_name"));
        ps.setPay(rs.getDouble("totalpay"));
        ps.setPaydate(rs.getDate("paydate"));
        ps.setRowID(rs.getInt("rowid"));

        return ps;
    }
}
