/*
 * package com.example.S20230403.controller.lcgController;
 * 
 * import java.util.List;
 * 
 * import org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.RequestParam;
 * 
 * import com.example.S20230403.model.Event; import
 * com.example.S20230403.service.lcgService.EventService;
 * 
 * import lombok.RequiredArgsConstructor;
 * 
 * @RequiredArgsConstructor
 * 
 * @Controller public class EventController { private final EventService
 * service;
 * 
 * @GetMapping("/noti") public String getEvent(@RequestParam("noti_id") int
 * noti_id, Event event, Model model) { System.out.println("컨트롤러 getevent 시작");
 * System.out.println("컨트롤러 getevent noti_id-> "+noti_id);
 * 
 * if(noti_id == 0) { return ""; }else if(noti_id == 1){ return ""; }else {
 * List<Event> sukbakEvents = service.getEvent();
 * System.out.println("이벤트 사이즈 -> "+sukbakEvents.size());
 * model.addAttribute("sukbakEvents", sukbakEvents); return
 * "/views/events/sukbakEvents"; } } }
 */