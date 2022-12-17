package avada.media.myhouse24_client.controller.pages;

import avada.media.myhouse24_client.model.dto.TariffDTO;
import avada.media.myhouse24_client.service.FlatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/tariffs")
@RequiredArgsConstructor
public class TariffsPageController {

    private final FlatService flatService;

    @GetMapping({"/**", ""})
    public ModelAndView showFlatTariffsPage() {
        return new ModelAndView("pages/tariffs");
    }

    @GetMapping("get-tariff-by-flat")
    public @ResponseBody TariffDTO getTariffByFlatId(@RequestParam Long flatId) {
        return flatService.getTariffDTOByFlat(flatId);
    }

}
