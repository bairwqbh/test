package per.cby.test.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * 
 * @author chenboyang
 * @since 2020年3月26日
 *
 */
@Data
@Accessors(chain = true)
public class Test {

    @NotBlank
    @Size(max = 32)
    private String id;

    @NotBlank
    @Size(max = 32)
    private String name;

}
