package com.qi.management.service.write;

import com.qi.management.model.domain.BaseButton;
import com.qi.management.model.dto.ButtonDto;

/**
 * Class ButtonWriteService
 *
 * @author 张麒 2018-2-1.
 * @version Description:
 */
public interface ButtonWriteService {

    void save(BaseButton model);

    void changeStatus(ButtonDto model);
}
