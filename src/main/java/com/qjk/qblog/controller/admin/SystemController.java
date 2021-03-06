package com.qjk.qblog.controller.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.qjk.qblog.data.Setting;
import com.qjk.qblog.data.SettingOption;
import com.qjk.qblog.service.ISettingService;
import com.qjk.qblog.util.Value;

@Controller
@RequestMapping(value = "/admin/system")
public class SystemController {

	@Resource
	ISettingService settingService;

	@RequestMapping(value = { "/setting", "setting/" }, method = RequestMethod.GET)
	public String setting(Model model) {

		return setting(model, 0);
	}

	@RequestMapping(value = { "/setting/{id}", "/setting/{id}/" }, method = RequestMethod.GET)
	private String setting(Model model, @PathVariable long id) {

		List<Setting> settings = settingService.getSettings();
		Setting currSetting = null;
		if (settings != null && settings.size() > 0) {
			if (id != 0) {
				currSetting = settingService.getSettingById(id);
			}
			if (currSetting == null) {
				currSetting = settings.get(0);
			}
		}
		model.addAttribute("settings", settings);
		model.addAttribute("currSetting", currSetting);

		return "admin/system/setting";
	}

	@RequestMapping(value = { "/setting", "/setting/" }, method = RequestMethod.POST)
	private String setting(Model model, @RequestParam("id") long id,
			@RequestParam("name") String[] names,
			@RequestParam("value") String[] values,
			@RequestParam("summary") String[] summarys) {

		Setting setting = settingService.getSettingById(id);

		if (setting == null) {
			return "redirect:/admin/system/setting";
		}
		buildOptions2(setting, names, values, summarys);
		settingService.updateSetting(setting);

		return "redirect:/admin/system/setting/" + setting.getSettingId();
	}

	public void buildOptions(Setting setting, String[] names, String[] values,
			String[] summarys) {

		if (setting == null || names == null || names.length == 0) {

		} else {
			List<SettingOption> list = new ArrayList<SettingOption>(
					names.length);

			for (int i = 0; i < names.length; i++) {
				String name = names[i];

				if (Value.isEmpty(name)) {
					continue;
				}
				Stream<SettingOption> stream = setting.getOptions().stream();
				SettingOption option = stream
						.filter(o -> o.getName().equals(name)).findFirst()
						.get();
				if (option == null) {
					option = new SettingOption();
				}
				option.setName(Value.stringValue(names[i], ""));

				if (values != null && values.length > i) {
					option.setValue(Value.stringValue(values[i], ""));
				}
				if (summarys != null && summarys.length > i) {
					option.setSummary(Value.stringValue(summarys[i], ""));
				}
				option.setSetting(setting);
				list.add(option);

			}
			setting.setOptions(list);

		}

	}

	private void buildOptions2(Setting setting, String[] names,
			String[] values, String[] summarys) {

		if (setting == null || names == null || names.length == 0) {

		} else {
			
			Stream<String> nameStream = Arrays.stream(names);
			int[] index = { -1 };
			List<SettingOption> list = nameStream
					.filter((name) -> {
						index[0]++;
						return !Value.isEmpty(name);
					})
					.map((name) -> {
						Stream<SettingOption> optionStream = setting.getOptions().stream();
						SettingOption option = optionStream.filter(o -> {
							return o.getName().equals(name);
						}).findFirst().get();
						if (option == null) {
							option = new SettingOption();
						}
						option.setName(Value.stringValue(name, ""));
						if (values != null && values.length > index[0]) {
							option.setValue(Value.stringValue(values[index[0]],
									""));
						}
						if (summarys != null && summarys.length > index[0]) {
							option.setSummary(Value.stringValue(
									summarys[index[0]], ""));
						}
						option.setSetting(setting);

						return option;
					}).collect(Collectors.toList());

			setting.setOptions(list);
		}

	}
}
