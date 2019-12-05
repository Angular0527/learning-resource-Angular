"use strict";
const {
  dialogflow,
  List,
  BasicCard,
  Suggestions,
  Button,
  Image,
  LinkOutSuggestion,
  SimpleResponse
} = require("actions-on-google");
const app = dialogflow({ debug: true });
const functions = require("firebase-functions");
const committeeMembersData = require("./data/aboutMembers.json");
const Speakers = require("./data/Speakers.json");
const Mobile = require("./data/mobile.json");
const Web = require("./data/web.json");
const CodeLab = require("./data/codeLab.json");

app.intent("New Welcome Intent", conv => {
  conv.ask(
    new SimpleResponse({
      speech: `The GDG Ahmedabad Action welcomes you. How can I help you today?`,
      text: `The GDG Ahmedabad Action welcomes you. How can I help you today?`
    }),
    new Suggestions([`DevFest 2k18`, `About GDG Ahmedabad`, `WTM`])
  );
});

app.intent("AboutGDGIntent", conv => {
  // conv.ask(
  //   `<speak> <sub alias="Google Developers Group">GDG</sub> Ahmedabad is open and volunteer geek community who create exciting projects and share experience about Google technologies with a passion.</speak>`
  // );
  conv.ask(`<speak>Here's the information about GDG Ahmedabad</speak>`);
  conv.ask(
    new BasicCard({
      text: `GDG Ahmedabad is open and volunteer geek community who create exciting projects and share experience about Google technologies with a passion`,
      subtitle: "Founded and Organized by Paresh Mayani",
      title: "About GDG Ahmedabad",
      buttons: new Button({
        title: "Visit Website",
        url: "https://www.gdgahmedabad.com/"
      }),
      image: new Image({
        url: "https://avatars1.githubusercontent.com/u/16831892?s=280&v=4",
        alt: "GDG Ahmedabad Icon"
      }),
      display: "CROPPED"
    })
  );

  conv.ask(
    new Suggestions([`meet the team`, `DevFest 2018`, `contribute`]),
    new LinkOutSuggestion({
      name: `PastEvents Highlight`,
      url: "https://www.meetup.com/GDG-Ahmedabad/events/past/"
    })
  );
});

app.intent("DevFestIntent", conv => {
  conv.ask(`<speak>Here's the information about DevFest</speak>`);
  conv.ask(
    new BasicCard({
      text: `DevFests are community-led, developer events hosted by GDG chapters around the globe focused on community building and learning about Google’s technologies.`,
      subtitle: "Happens during August 1 — November 30, 2018",
      title: "About DevFest",
      buttons: new Button({
        title: "Visit Website",
        url: "http://devfest.gdgahmedabad.com/"
      }),
      image: new Image({
        url: "https://avatars1.githubusercontent.com/u/16831892?s=280&v=4",
        alt: "DevFest Icon"
      }),
      display: "CROPPED"
    })
  );
  conv.ask(
    new Suggestions([
      `List of Web Events`,
      `List of Mobile Events`,
      `List of CodeLabs`,
      `Venue`,
      `Swags`,
      `Women Speakers`,
      `Speakers of DevFest 2018`
    ]),
    new LinkOutSuggestion({
      name: `Navigate to Venue`,
      url: "https://goo.gl/maps/wcJ3dEjWKQs"
    })
  );
});

app.intent("WTMInfo", conv => {
  conv.ask(
    `<speak>Here's the information about <sub alias="Women Tech Makers">WTM</sub></speak>`
  );
  conv.ask(
    new BasicCard({
      text: `A community encouraging women in tech, making Ahmedabad tech community diverse & comprehensive, share knowledge and passion through sessions, talks, workshops.`,
      subtitle:
        "Dhurva Shastri is a woman in tech and playing a role as an organizer and WTM Lead at GDG/WTM Ahmedabad",
      title: "About WTM",
      buttons: new Button({
        title: "Visit Website",
        url: "https://www.womentechmakers.com/"
      }),
      image: new Image({
        url:
          "https://lh4.googleusercontent.com/m-macIPNQbE_Z1tSdViidBMEcCWF6n0dEk5XIIFWclIAnmgGGfnMCgphlxKdmflFuBqYtQ=w1200-h630-p",
        alt: "WTM Icon"
      }),
      display: "WHITE"
    })
  );
  // conv.ask(new Suggestions([`List of Day 1 Events`, `List of Day 2 Events`]));
  conv.ask(
    new LinkOutSuggestion({
      name: "Twitter: WTM A'bad",
      url: "https://www.twitter.com/wtmahmedabad"
    }),
    new Suggestions([`About Dhruva Shastri`, `Exit`])
  );
});

app.intent("eventIntent", (conv, params) => {
  // const eventType = conv.body.queryResult.parameters.eventType;
  const result = {
    title: ``,
    items: {}
  };
  const eventType = params.eventType;
  result.title = `${eventType}`;
  if (eventType === "MOBILE") {
    const dataArray = Object.entries(Mobile);
    for (const data of dataArray) {
      const key = data[0];
      const value = data[1];
      result.items[`${value.title} ${key}`] = {
        synonyms: [`${value.title}, ${value.time}`],
        title: `At ${value.time}: ${value.title}`,
        description: `${value.speaker}, Duration:${value.duration}`
      };
    }
    conv.ask(`<speak>Events of Mobile Track </speak>`);
    conv.ask(
      new List(result),
      new Suggestions([`web track`, `CodeLab schedule`, `venue`])
    );
    // conv.ask(new Suggestions(`send me talk updates`));
  } else if (eventType === "WEB") {
    const dataArray = Object.entries(Web);
    for (const data of dataArray) {
      const key = data[0];
      const value = data[1];
      result.items[`${value.title} ${key}`] = {
        synonyms: [`${value.title}, ${value.time}`],
        title: `At ${value.time}: ${value.title}`,
        description: `${value.speaker ? value.speaker + "," : ""} Duration:${
          value.duration
        }`
      };
    }
    conv.ask(`<speak>Events of Web track</speak>`);
    conv.ask(
      new List(result),
      new Suggestions([`mobile track`, `CodeLab schedule`, `venue`])
    );
    // conv.ask(new Suggestions(`send me talk updates`));
  } else if (eventType === "CODELAB") {
    const dataArray = Object.entries(CodeLab);
    for (const data of dataArray) {
      const key = data[0];
      const value = data[1];
      result.items[`${value.title} ${key}`] = {
        synonyms: [`${value.title}, ${value.time}`],
        title: `At ${value.time}: ${value.title}`,
        description: `${value.speaker}, Duration:${value.duration}`
      };
    }
    conv.ask(`<speak>CodeLabs Schedule</speak>`);
    conv.ask(
      new List(result),
      new Suggestions([`mobile track`, `web track`, `venue`])
    );
    // conv.ask(new Suggestions(`send me talk updates`));
  }
});

app.intent("gdgCommittee", (conv, params) => {
  // const name = conv.body.queryResult.parameters.committeeMembers;
  const name = params.committeeMembers;
  const keys = Object.keys(committeeMembersData);

  const COMMITTEE_MEMBERS = [
    `Paresh Mayani`,
    `Dhrumil Shah`,
    `Chintan Rathod`,
    `Jaldeep Asodariya`,
    `Dhurva Shastri`,
    `Utpal Betai`,
    `Pratik Patel`
  ];
  let chips = [];
  COMMITTEE_MEMBERS.forEach(names => {
    if (names !== name) {
      chips.push(`About ${names}`);
    }
  }, COMMITTEE_MEMBERS);

  chips.unshift(`contribute`);

  keys.forEach(key => {
    if (key === name) {
      // handleNotFound = 0;
      // console.log(`${key} found`);
      // console.log(committeeMembersData[`${key}`]["intro"]);
      conv.ask(`<speak>${name}'s Profile.</speak>`);
      conv.ask(
        new BasicCard({
          text: committeeMembersData[`${key}`]["intro"],
          subtitle: `About`,
          title: key,
          buttons: new Button({
            title: "Visit Twitter Profile",
            url: committeeMembersData[`${key}`]["twitter"]
          }),
          image: new Image({
            url: committeeMembersData[`${key}`]["image"],
            alt: "Profile Picture"
          }),
          display: "CROPPED"
        })
      );
      // break;
    }
  });
  // if (handleNotFound) {
  //   conv.ask(
  //     `<speak>I'm Sorry!, Seems like I'll have to update my database soon!</speak>`
  //   );
  // }
  conv.ask(
    new LinkOutSuggestion({
      name: `PastEvents Highlight`,
      url: "https://www.meetup.com/GDG-Ahmedabad/events/past/"
    }),
    new Suggestions(chips)
  );
});

app.intent("getAllMembers", conv => {
  conv.ask("List of all GDG Ahmedabad Committee Members");
  conv.ask(
    new List({
      title: "Committee Members",
      items: {
        PARESH_MAYANI: {
          synonyms: ["Paresh Mayani", "Paresh Sir", "Mayani Sir"],
          title: "Paresh Mayani",
          description: "Founder of GDG Ahmedabad",
          image: new Image({
            url:
              "https://pbs.twimg.com/profile_images/447806646228488193/52264v-L_400x400.jpeg",
            alt: "Paresh Mayani's Picture"
          })
        },
        DHRUMIL_SHAH: {
          synonyms: ["Dhrumil Shah", "Dhrumil Sir", "Shah Sir"],
          title: "Dhrumil Shah",
          description: "Co-organizer at GDG Ahmedabad",
          image: new Image({
            url:
              "https://pbs.twimg.com/profile_images/654186562230468608/xEBDps3u_400x400.jpg",
            alt: "Dhrumil Shah's Picture"
          })
        },
        CHINTAN_RATHOD: {
          synonyms: ["Chintan Rathod", "Chintan Sir", "Rathod Sir"],
          title: "Chintan Rathod",
          description: "Co-organizer at GDG Ahmedabad",
          image: new Image({
            url:
              "https://pbs.twimg.com/profile_images/987564553536921600/HMQDpWmE_400x400.jpg",
            alt: "Chintan Rathod's Picture"
          })
        },
        JALDEEP_ASODARIYA: {
          synonyms: ["Jaldeep Asodariya", "Jaldeep sir", "Asodariya sir"],
          title: "Jaldeep Asodariya",
          description: "Core Organizing Team Member at GDG Ahmedabad",
          image: new Image({
            url:
              "https://pbs.twimg.com/profile_images/898486597175263232/U_9clmww_400x400.jpg",
            alt: "Jaldeep Asodariya's Picture"
          })
        },
        DHRUVA_SHASTRI: {
          synonyms: ["Dhruva Shastri", "Dhruva madam", "Shastri madam"],
          title: "Dhruva Shastri",
          description: "Leads WTM Ahmedabad",
          image: new Image({
            url:
              "https://pbs.twimg.com/profile_images/972084540679241729/XyWmFtij_400x400.jpg",
            alt: "Dhruva Shastri's Picture"
          })
        },
        PRATIK_PATEL: {
          synonyms: ["Pratik Patel", "Pratik sir", "Patel Sir"],
          title: "Pratik Patel",
          description: "Co-organizer at GDG Ahmedabad",
          image: new Image({
            url:
              "https://pbs.twimg.com/profile_images/1011441747073757184/QYXDujoz_400x400.jpg",
            alt: "Pratik Patel's Picture"
          })
        },
        UTPAL_BETAI: {
          synonyms: ["Utpal Betai", "Utpal sir", "Betai Sir"],
          title: "Utpal Betai",
          description: "Assistant organizer at GDG Ahmedabad",
          image: new Image({
            url:
              "https://pbs.twimg.com/profile_images/886454117534531584/IJh9MqIa_400x400.jpg",
            alt: "Uptal Betai's Picture"
          })
        }
      }
    }),
    new Suggestions([
      `About Paresh Mayani`,
      `About Dhrumil Shah`,
      `About Chintan Rathod`,
      `About Jaldeep Asodariya`,
      `About Dhurva Shastri`,
      `About Utpal Betai`,
      `About Pratik Patel`
    ])
  );
});

app.intent("EventDates", conv => {
  conv.ask(
    new SimpleResponse({
      speech: `This year GDG Ahmedabad's DevFest is on 25th November 2018 and the venue is Courtyard by Mariott Ahmedabad`,
      text: `GDG Ahmedabad's DevFest 2018 is on 25th November 2018 and the venue is Courtyard by Mariott Ahmedabad`
    }),
    new Suggestions([`List of Speakers`, `Technologies`]),
    new LinkOutSuggestion({
      name: `Navigate to Venue`,
      url: "https://goo.gl/maps/wcJ3dEjWKQs"
    })
  );
});
app.intent("VenueIntent", conv => {
  conv.ask(
    new SimpleResponse({
      speech: `This year's GDG ahmedabad's devfest is hosted at Courtyard by Mariott`,
      text: `This year's GDG ahmedabad's devfest is hosted at Courtyard by Mariott`
    }),
    new Suggestions([
      `list of web events`,
      `list of mobile events`,
      `CodeLab Schedule`
    ]),
    new LinkOutSuggestion({
      name: `Navigate to Venue`,
      url: "https://goo.gl/maps/wcJ3dEjWKQs"
    })
  );
});

app.intent("SwagIntent", conv => {
  conv.ask(
    new SimpleResponse({
      speech: `Attendees this year will get DevFest Tshirt and a badge`,
      text: `Attendees this year will get DevFest Tshirt and a badge`
    }),
    new Suggestions([`Search for talks`, `Venue`])
  );
});

app.intent("SessionsIntent", conv => {
  conv.ask(
    new SimpleResponse({
      speech: `Which topic are you interested in?`,
      text: `Which topic are you interested in?`
    }),
    new Suggestions([`Mobile Track`, `Web Track`, `Codelab Schedule`])
  );
});

app.intent("OrganizerIntent", conv => {
  conv.ask(
    new SimpleResponse({
      speech: `GDG Ahmedabad will host the event and GDG ahmedabad along with WTM ahmedabad will organize this year's devfest`,
      text: `GDG Ahmedabad will host the event and GDG ahmedabad along with WTM ahmedabad will organize this year's devfest`
    }),
    new Suggestions([`Venue`, `Swag this year`, `Browse talks`]),
    new LinkOutSuggestion({
      name: `Navigate to Venue`,
      url: "https://goo.gl/maps/wcJ3dEjWKQs"
    })
  );
});

app.intent("SpeakersIntent", conv => {
  const result = {
    title: `List of Speakers`,
    items: {}
  };
  const dataArray = Object.entries(Speakers);
  for (const data of dataArray) {
    const key = data[0];
    const value = data[1];
    result.items[`${key}`] = {
      synonyms: [`${key}`, `${value.intro}`],
      title: `${value.type}: ${value.topic} By ${key}`,
      description: `${key}, ${value.intro}`,
      image: new Image({
        url: `${value.image}`,
        alt: `${key}'s Picture`
      })
    };
  }
  conv.ask("<speak>Here are the Speakers of Devfest 2018</speak>");
  conv.ask(
    new List(result),
    new Suggestions(
      `Women Speakers`,
      `List of web events`,
      `List of mobile events`,
      `Codelab Schedule`
    )
  );
});

app.intent("WomenInTech", conv => {
  const result = {
    title: `List of Women Speakers`,
    items: {}
  };
  const dataArray = Object.entries(Speakers);
  for (const data of dataArray) {
    const key = data[0];
    const value = data[1];
    if (value.gender === "Female")
      result.items[`${key}`] = {
        synonyms: [`${key}`, `${value.intro}`],
        title: `${value.type}: ${value.topic} By ${key}`,
        description: `${key}, ${value.intro}`,
        image: new Image({
          url: `${value.image}`,
          alt: `${key}'s Picture`
        })
      };
  }
  conv.ask("<speak>Here are the Women Speakers of Devfest 2018</speak>");
  conv.ask(
    new List(result),
    new Suggestions(
      `All Speakers`,
      `List of web events`,
      `List of mobile events`,
      `Codelab Schedule`
    )
  );
});

app.intent("ContributeIntent", conv => {
  conv.ask("More information about contribution");
  conv.ask(
    new BasicCard({
      text: `GDG Ahmedabad is open and volunteer geek community who create exciting projects and share experience about Google technologies with a passion`,
      subtitle: "Founded and Organized by Paresh Mayani",
      title: "Contribute to GDG Ahmedabad",
      buttons: new Button({
        title: "Visit Website",
        url: "https://github.com/GDGAhmedabad"
      }),
      image: new Image({
        url: "https://avatars1.githubusercontent.com/u/16831892?s=280&v=4",
        alt: "GDG Ahmedabad Icon"
      }),
      display: "CROPPED"
    })
  );
  conv.ask(
    new Suggestions([`meet the team`, `DevFest 2018`]),
    new LinkOutSuggestion({
      name: `PastEvents Highlight`,
      url: "https://www.meetup.com/GDG-Ahmedabad/events/past/"
    })
  );
});

// https://firebase.google.com/docs/functions/write-firebase-functions

// app.fallback(conv => {
//   // intent contains the name of the intent
//   // you defined in the Intents area of Dialogflow
//   const intent = conv.intent;
//   switch (intent) {
//     case WELCOME_INTENT:
//       conv.ask("Welcome! Say a number.");
//       break;

//     case NUMBER_INTENT:
//       const num = conv.arguments.get(NUMBER_ARGUMENT);
//       conv.close(`You said ${num}`);
//       break;
//   }
// });

exports.dialogflowFirebaseFulfillment = functions.https.onRequest(app);
