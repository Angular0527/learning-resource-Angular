// const committeeMembersData = require("./functions/data/aboutMembers.json");
// const speakers = require("./functions/data/Speakers.json");
// // function hello() {
// //   const name = "Paresh Mayani";
// //   const keys = Object.keys(committeeMembersData);
// //   // console.log("1");
// //   console.log(keys);
// //   // for (const key in keys) {
// //   //   console.log(key);
// //   //   if (key === name) {
// //   //     // console.log("3");
// //   //     console.log(`${key} found`);
// //   //     console.log(committeeMembersData[keys][intro]);
// //   //   }
// //   // console.log("4");
// //   // }
// //   keys.forEach(key => {
// //     if (key === name) {
// //       console.log(`${key} found`);
// //       console.log(committeeMembersData[`${key}`]["intro"]);
// //     }
// //   });
// // }
// // hello();

// const dataArray = Object.entries(speakers);

// // for (const data of dataArray) {
// //   console.log(`---------------------------`);
// //   console.log(`Speaker Title: ${data[0]}`);
// //   console.log(`Intro: ${data[1].intro}`);
// //   console.log(`Type: ${data[1].type}`);
// //   console.log(`Topic: ${data[1].topic}`);
// // }
// const result = {
//   title: `List of Speakers`,
//   items: {}
// };
// // const dataArray = Object.entries(Speakers);
// for (const data of dataArray) {
//   const key = data[0];
//   const value = data[1];
//   if (value.gender === "Female")
//     result.items[`${key}`] = {
//       synonyms: [`${key}`, `${value.intro}`],
//       title: `${value.type}: ${value.topic}`,
//       description: `By ${key}, ${value.intro}`
//       // image: new Image({
//       //   url: `${value.image}`,
//       //   alt: `${key}'s Picture`
//       // })
//     };
// }
// console.log(result);
const name = "Dhrumil Shah";
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

console.log(chips);
