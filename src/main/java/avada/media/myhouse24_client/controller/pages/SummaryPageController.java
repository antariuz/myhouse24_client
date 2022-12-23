package avada.media.myhouse24_client.controller.pages;

import avada.media.myhouse24_client.service.FlatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/summary")
@RequiredArgsConstructor
public class SummaryPageController {

    private final FlatService flatService;

    @GetMapping({"/**", ""})
    public ModelAndView showFlatSummaryPage() {
        return new ModelAndView("pages/summary");
    }

    @GetMapping("get-user-flat-expenses-info")
    public @ResponseBody Map<String, Object> getUserFlatExpensesInfo(Long flatId) {
        return flatService.getUserFlatExpensesInfo(SecurityContextHolder.getContext().getAuthentication().getName(), flatId);
    }

}
