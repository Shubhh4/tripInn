package com.example.TripInn.controller;

//import ch.qos.logback.core.model.Model;
import org.springframework.ui.Model;
import com.example.TripInn.entity.Room;
import com.example.TripInn.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public String getAllRooms(Model model){
        List<Room> rooms=roomService.getAllRooms();
        model.addAttribute("rooms",rooms);
        return "room-list";
    }

    @GetMapping("/{id}")
    //if room by the id is not, then throw the exception otherwise get the room object and pass by the attribute

    public String getRoomById(@PathVariable Long id, Model model){
        Room room = roomService.getRoomById(id).orElseThrow(()-> new IllegalArgumentException("Invalid room id: " + id));
        model.addAttribute("room", room);
        return "room-details";
    }

    @GetMapping("/room-number/{roomNumber}")
    public String getRoomByRoomNumber(@PathVariable int roomNumber, Model model){
        Room room = roomService.getRoomByRoomNumber(roomNumber).orElseThrow(()-> new IllegalArgumentException("Invalid room number: " + roomNumber));
        model.addAttribute("room",room);
        return "room-details";
    }

    @GetMapping("/new")
    public String showAddRoomForm(Model model){
        model.addAttribute("room",new Room());
        return "room-form";
    }


    @PostMapping
    public String addRoom(Room room, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            return "room-form";
        }
        roomService.saveOrUpdateRoom(room);
        redirectAttributes.addFlashAttribute("successMessage", "Room added successfully");
        return "redirect:/rooms";
    }

    @GetMapping("/edit{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        Room room = roomService.getRoomById(id).orElseThrow(()-> new IllegalArgumentException("Invalid room id: " + id));
        model.addAttribute("room", room);
        return "room-form";
    }

    @PostMapping("/edit/{id}")
    public String updateRoom(@PathVariable Long id, Room room, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            return "room-form";
        }
        room.setId(id);
        roomService.saveOrUpdateRoom(room);
        redirectAttributes.addFlashAttribute("successMessage", "Room updated successfully");
        return "redirect:/rooms";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id, RedirectAttributes redirectAttributes){
        roomService.deleteRoom(id);
        redirectAttributes.addFlashAttribute("successMessage", "Room delete successfully");
        return "redirect:/rooms";
    }

}
