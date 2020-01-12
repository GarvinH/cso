class Schedule:
    def __init__(self,registration_term,crn,subject,long_title,title,stype,
    status,meeting_date,days,time,building,room,instructor):
        self.registration_term = registration_term,
        self.crn = crn,
        self.subject = subject,
        self.long_title = long_title,
        self.title = title,
        self.stype = stype,
        self.status = status,
        self.meeting_date = meeting_date,
        self.days = days ,
        self.time = time  ,
        self.building = building,
        self.room = room,
        self.instructor = instructor
    
    def get_scheduler_format(self):
        data = {
            "subject":self.subject,
            "time":self.time,
            "stype":self.stype,
            "days":self.days
        }
        return data
    
    def get_webpage_format(self):
        data = {
            "subject":self.subject,
            "crn":self.crn,
            "building":self.building,
            "instructor":self.instructor,
            "time":self.time,
            "days":self.days,
        }
        return data

    def get_master_format(self):
        data = {  
            "registration_term" : self.registration_term,
            "crn":self.crn,
            "subject":self.subject,
            "long_title":self.long_title,
            "title":self.title,
            "stype":self.stype,
            "status":self.status,
            "meeting_date":self.meeting_date,
            "days":self.days,
            "time":self.time,
            "building":self.building,
            "room":self.room,
            "instructor":self.instructor
        }
        return data
    