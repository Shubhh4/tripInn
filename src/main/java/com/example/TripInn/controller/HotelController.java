package com.example.TripInn.controller;


import com.example.TripInn.entity.Hotel;
import com.example.TripInn.entity.Room;
import com.example.TripInn.service.HotelService;
import com.example.TripInn.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RoomService roomService;

    @GetMapping
    public String getAllHotels(Model model){
        model.addAttribute("hotels", hotelService.getAllHotels());
        return "hotel-list";
    }

    @GetMapping("/{id}")
    public String getHotelById(@PathVariable Long id, Model model){
        Hotel hotel = hotelService.getHotelById(id).orElseThrow(()->new IllegalArgumentException("Invalid hotel id:"));
        model.addAttribute("hotel", hotel);
        return "hotel-details";
    }

    @GetMapping("/city/{city}")
    public String getHotelsByCity(@PathVariable String city, Model model){
        List<Hotel> hotels = hotelService.getHotelsByCity(city);
        model.addAttribute("hotels", hotels);
        return "hotel-list";
    }

    @GetMapping("/new")
    public String showHotelForm(Model model){
        model.addAttribute("hotel", new Hotel());
        return "hotel-form";
    }

    @GetMapping("/new/room/{id}")
    public String showHotelRoomForm(Model model,@PathVariable Long id){
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("hotelId", id);
        return "hotel-room-form";
    }

    @GetMapping("/rooms/{id}")
    public String showHotelRooms(Model model, @PathVariable Long id){
        List <Room> roomList=roomService.getRoomsByHotelId(id);
        model.addAttribute("rooms", roomList);
        model.addAttribute("hotelId",id);
        model.addAttribute("rooms", new Room());
        return "hotel-room-list";
    }

    @PostMapping("/add-room/{id}")
    public String updateRoomToHotel(@PathVariable Long id,Room room, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            return "hotel-form";
        }
        Hotel hotel = hotelService.getHotelById(id).orElseThrow(()-> new IllegalArgumentException("Invalid hotel id: " +id));
        room.setHotel(hotel);


        roomService.saveOrUpdateRoom(room);
        redirectAttributes.addFlashAttribute("successMessage", "Hotel updated successfully");
        return "redirect:/hotels";
    }

    @PostMapping
    public String addHotel(Hotel hotel, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            return "hotel-form";
        }hotelService.saveOrUpdateHotel(hotel);
        redirectAttributes.addFlashAttribute("successMessage", "Hotel added successfully");
        return "redirect:/hotels"; //Redirect to hotel list page
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        Hotel hotel = hotelService.getHotelById(id).orElseThrow(()-> new IllegalArgumentException("Invalid hotel id: " + id));
        model.addAttribute("hotel", hotel);
        return "hotel-form";
    }

    @PostMapping("/edit/{id}")
    public String updateHotel(@PathVariable Long id, Hotel hotel,BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            return "hotel-form";
        }hotel.setId(id);  //To ensure correct ID is set
        hotelService.saveOrUpdateHotel(hotel);
        redirectAttributes.addFlashAttribute("successMessage", "Hotel updated successfully");
        return "redirect:/hotels";
    }









}
