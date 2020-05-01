from tkinter import *
from tkinter import ttk
import termOptionsScrape
import json

def mainWindow():
    print(comboBox.current());
    termSelect.destroy();
    window = Tk();
    window.title("Carleton Schedule Optimiser");
    window.geometry("1280x720");
    window.mainloop();

termOptionsScrape.termOptionsScrape();
termText = []
termID=[]
termOptions = json.load(open("./termOptions.json"))
for options, children in termOptions.items():
    termText.append(children["text"]);
    termID.append(children["termNum"]);

print(termText);

termSelect = Tk();
termSelect.title("Carleton Schedule Optimiser");
label = Label(termSelect, text="Please select the term");
label.grid(row=0,column = 0);
comboBox = ttk.Combobox(termSelect, state="readonly", values=termText, width=len(max(termText, key=len)));
comboBox.grid(row=1,column = 0);
comboBox.current(0);
button = Button(termSelect, text="OK", command=mainWindow);
button.grid(row=2,column = 0);
termSelect.mainloop();