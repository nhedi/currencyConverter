package converter.presentation.conv;

import converter.presentation.error.ExceptionHandlers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import converter.application.ConverterService;
import converter.domain.ConversionDTO;

import javax.validation.Valid;

@Controller
@Scope("session")
public class CurrencyController {
    static final String DEFAULT_PAGE_URL = "/";
    static final String SELECT_CON_PAGE_URL = "select-conversion";
    static final String UPDATE_RATE_URL = "update-rate";

    private static final String CURRENT_CON_OBJ_NAME = "currentConversion";
    private static final String FIND_CONVERSION_FORM_OBJ_NAME = "findConversionForm";

    private String conversionResult;

    @Autowired
    private ConverterService service;
    private ConversionDTO currentConv;

    @GetMapping(DEFAULT_PAGE_URL)
    public String showDefaultView() {
        return "redirect:" + SELECT_CON_PAGE_URL;
    }

    @GetMapping("/" + SELECT_CON_PAGE_URL)
    public String showCurrencySelectionView(FindConversionForm findConversionForm) {
        return SELECT_CON_PAGE_URL;
    }

    private String showConversionResultPage(Model model) {
        if (currentConv != null) {
            model.addAttribute(CURRENT_CON_OBJ_NAME, currentConv);
        }
        return SELECT_CON_PAGE_URL;
    }

    @PostMapping("/" + SELECT_CON_PAGE_URL)
    public String findExchangeRate(@Valid FindConversionForm findConversionForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(FIND_CONVERSION_FORM_OBJ_NAME, new FindConversionForm());
            return SELECT_CON_PAGE_URL;
        }
        String from = findConversionForm.getFrom();
        int amount = findConversionForm.getNumber();

        String to = findConversionForm.getTo();
        currentConv = service.findConversion(from + "" + to);
        double rate = currentConv.getRate();
        findConversionForm.setConversionResult(rate * amount);
        conversionResult = findConversionForm.getConversionResult();
        int newCount = currentConv.getCount() + 1;
        service.setNewCount(newCount, from + "" + to);

        if (currentConv == null) {
            model.addAttribute(ExceptionHandlers.ERROR_TYPE_KEY, ExceptionHandlers.NO_CONVERSION_FOUND);
            model.addAttribute(ExceptionHandlers.ERROR_INFO_KEY, ExceptionHandlers.NO_CONVERSION_FOUND_INFO);
            return ExceptionHandlers.ERROR_PAGE_URL;
        }
        return showConversionResultPage(model);
    }

    @GetMapping("/" + UPDATE_RATE_URL)
    public String showUpdateRateView(FindConversionForm findConversionForm) {
        int count = service.countSum();
        findConversionForm.setTotalCount(count);
        return UPDATE_RATE_URL;
    }

    private String showUpdateRateResultPage(Model model) {
        if (currentConv != null) {
            model.addAttribute(CURRENT_CON_OBJ_NAME, currentConv);
        }
        return UPDATE_RATE_URL;
    }

    @PostMapping("/" + UPDATE_RATE_URL)
    public String setExchangeRate(@Valid FindConversionForm findConversionForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(CURRENT_CON_OBJ_NAME, new FindConversionForm());
            return UPDATE_RATE_URL;
        }

        String from = findConversionForm.getFrom();
        double rate = findConversionForm.getRate();
        String to = findConversionForm.getTo();
        service.setNewRate(rate,from + "" + to);
        currentConv.setNewRate(rate);
        int count = service.countSum();
        findConversionForm.setTotalCount(count);

        if (currentConv == null) {
            model.addAttribute(ExceptionHandlers.ERROR_TYPE_KEY, ExceptionHandlers.NO_CONVERSION_FOUND_FOR_UPDATE);
            model.addAttribute(ExceptionHandlers.ERROR_INFO_KEY, ExceptionHandlers.NO_CONVERSION_FOUND_FOR_UPDATE_INFO);
            return ExceptionHandlers.ERROR_PAGE_URL;
        }
        return showUpdateRateResultPage(model);
    }
}